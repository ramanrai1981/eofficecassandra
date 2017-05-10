(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('PendingfileController', PendingfileController);

    PendingfileController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function PendingfileController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        vm.addtags = [{"id": "10","name": "Noting"},{"id": "11","name": "DO"},{"id": "15","name": "General Note"}];
        console.log(vm.tags);

        vm.pendingfiles = [{"id": "10","fileno": "SK/30202","title": "PC purchase","tag": "Noting","username": "Shyam Sunder","departmentname": "Accounts"},
        {"id": "30","fileno": "SK/564402","title": "Tender for Chair Purchase","tag": "Office Order","username": "Mukesh Singh","departmentname": "HR Dept"},
        {"id": "30","fileno": "SK/90402","title": "Table Purchase","tag": "Office Order","username": "Mukesh Singh","departmentname": "MD Office"}];

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
