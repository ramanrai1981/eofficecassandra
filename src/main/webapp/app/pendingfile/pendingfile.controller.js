(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('PendingfileController', PendingfileController);

    PendingfileController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'File'];

    function PendingfileController ($scope, Principal, LoginService, $state, File) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        loadAllFiles();

        function loadAllFiles() {
            File.query(function(result) {
                vm.files = result;
                console.log(vm.files);

            });
        }

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
