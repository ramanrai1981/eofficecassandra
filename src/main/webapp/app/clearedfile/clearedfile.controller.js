(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('ClearedfileController', ClearedfileController);

    ClearedfileController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function ClearedfileController ($scope, Principal, LoginService, $state) {
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

        vm.clearedfiles = [{"id": "10","fileNo": "SK/30202","title": "PC purchase","tag": "Noting","fromusername": "Shyam Sunder","fromdepartmentname": "Accounts","byusername": "Madhwi","bydepartmentname": "MD Office","fileclosedby": "Shyam Sunder"},
        {"id": "30","fileNo": "SK/564402","title": "Tender for Chair Purchase","tag": "Office Order","fromusername": "Mukesh Singh","fromdepartmentname": "HR Dept","byusername": "Madhwi","bydepartmentname": "MD Office","fileclosedby": "Mukesh Singh"},
        {"id": "30","fileNo": "SK/90402","title": "Table Purchase","tag": "Office Order","fromusername": "Mukesh Singh","fromdepartmentname": "MD Office","byusername": "Sunanda","bydepartmentname": "Purchase Office","fileclosedby": "Mukesh Singh"}];

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
