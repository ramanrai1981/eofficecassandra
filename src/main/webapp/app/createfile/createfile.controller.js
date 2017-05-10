(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('CreatefileController', CreatefileController);


    CreatefileController.$inject = ['$scope', '$http', 'Principal', 'LoginService', '$state'];

    function CreatefileController ($scope, $http, Principal, LoginService, $state) {
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

        // to implement through tag and auto-complete
        $scope.tags = [{"tag": "Noting"},{"tag": "General Notice"},{"tag": "Office Order"}];
             $scope.loadTags = function(query) {
                return $http.get('tags.json');
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
