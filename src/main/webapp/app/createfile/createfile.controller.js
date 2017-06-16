(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('CreatefileController', CreatefileController);


    CreatefileController.$inject = ['$scope', '$http', 'Principal', 'file', 'filemovement', 'FileManagement', 'LoginService', '$state', 'File', 'FileMovement'];

    function CreatefileController ($scope, $http, Principal, file, filemovement, FileManagement, LoginService, $state, File, FileMovement) {
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

        // implemented through tags
        $scope.tags = [{"text": "Noting"},{"text": "General Notice"},{"text": "Office Order"},{"text": "DO Order"}];

        $scope.loadTags = function(query) {
//                return $http.get('/tags?query=' + query);
        return $http.get('#/createfile/tags.json');
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
