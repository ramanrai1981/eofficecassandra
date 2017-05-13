(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('AdddepartmentController', AdddepartmentController);

    AdddepartmentController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function AdddepartmentController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        vm.organisations = [{"id": "33","name": "Hartron"},{"id": "34","name": "ET & IT"}];

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
