(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('CreatefileController', CreatefileController);


    CreatefileController.$inject = ['$scope', '$http', 'Principal', 'file', 'LoginService', '$state', 'File'];

    function CreatefileController ($scope, $http, Principal, file, LoginService, $state, File) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        vm.file = file;
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
                vm.file.uploadDate = new Date();
                File.save(vm.file, onPutUpFileSuccess, onPutUpFileError);
            }
            vm.file = "";
            $state.reload();
        }

        function onPutUpFileSuccess (result) {
            $scope.$emit('eofficeApp:fileUpdate', result);
            vm.isSaving = false;

            vm.file =file;
            alert("File No:"+JSON.stringify(vm.file.fileNo)+"Title:"+JSON.stringify(vm.file.title)+"Put Up file Succeed.");
            $state.go('createfile');
            console.log(vm.file);
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
