(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('CreatefileController', CreatefileController);


    CreatefileController.$inject = ['$scope', 'Principal', 'file', 'filemovement', 'FileManagement', 'LoginService', '$state', 'File', 'FileMovement'];

    function CreatefileController ($scope, Principal, file, filemovement, FileManagement, LoginService, $state, File, FileMovement) {
        var vm = this;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.file = file;
        $scope.file = {};//scope object to hold file details
        vm.filemovement = filemovement;
        vm.putUpFile = putUpFile;
        $scope.$on('authenticationSuccess', function() {
        getAccount();
        });

        getAccount();

        vm.addtags = [{"id": "10","name": "Noting"},{"id": "11","name": "DO"},{"id": "15","name": "General Note"}];

        // to implement through tag and auto-complete
        $scope.tags = [{"tag": "Noting"},{"tag": "General Notice"},{"tag": "Office Order"}];
             $scope.loadTags = function(query) {
                return $http.get('tags.json');
        }

//        For Put Up File functionality

        function putUpFile () {
            vm.isSaving = true;
            if (vm.file.id !== null) {
                File.update(vm.file, onPutUpFileSuccess, onPutUpFileError);
            } else {
                File.save(vm.file, onPutUpFileSuccess, onPutUpFileError);
            }
        }

        function onPutUpFileSuccess (result) {
            $scope.$emit('eofficeApp:fileUpdate', result);
            vm.isSaving = false;

            var file= vm.fileDetail.file;
            FileManagement.saveFile(file, result.id);
            // Code to save the file in File Movement Entity//

            console.log(result.id);
            vm.isSaving = true;
            vm.filemovement.fileId = result.id;
            vm.filemovement.markFrom = result.id;
            vm.filemovement.markTo = result.id;
            vm.filemovement.actionStatus = "Pending";
            if (vm.filemovement.id !== null) {
                FileMovement.update(vm.filemovement, onFileMovementSaveSuccess, onFileMovementSaveError);
            } else {
                FileMovement.save(vm.filemovement, onFileMovementSaveSuccess, onFileMovementSaveError);
            }

            function onFileMovementSaveSuccess (result) {
                $scope.$emit('eofficeApp:fileMovementUpdate', result);
                vm.isSaving = false;
                console.log("fileId saved in file Movement entity");
                $state.go('createfile');
            }

            function onFileMovementSaveError () {
                vm.isSaving = false;
            }

        }

        function onPutUpFileError () {
            vm.isSaving = false;
            alert("Error... in Put Up File or You Did Not fill the details !");
        }

//        End Put Up File functionality

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
