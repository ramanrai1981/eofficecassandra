(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('PendingfilemarktoController', PendingfilemarktoController);

    PendingfilemarktoController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'File', 'selectedfiletomark'];

    function PendingfilemarktoController ($scope, Principal, LoginService, $state, File, selectedfiletomark) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        vm.selectedfiletomark = selectedfiletomark;
//        console.log(selectedfiletomark);

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();



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
