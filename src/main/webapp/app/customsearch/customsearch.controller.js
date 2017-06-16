(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('CustomsearchController', CustomsearchController);

    CustomsearchController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'File'];

    function CustomsearchController ($scope, Principal, LoginService, $state, File) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

//        $scope.selectedTitle = function(selected) {
//              if (selected) {
//                window.alert('You have selected ' + selected.title);
//              } else {
//                console.log('cleared');
//              }
//        };
//

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
