(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function HomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        vm.organisations = [{"name": "HARTRON","address": "Sec-17, Panchkula","establishyear": "1983"},
                            {"name": "Women & Child Development","address": "Sec-4, Panchkula","establishyear": "1966"},
                            {"name": "Secondary Education. Haryana","address": "Sec-5, Panchkula","establishyear": "1970"},
                            {"name": "HEPC","address": "Sec-2, Panchkula","establishyear": "2017"}];

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
