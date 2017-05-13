(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('AddemployeeController', AddemployeeController);

    AddemployeeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function AddemployeeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        vm.employees = [{"id": "101","empname":"Shaurya Kumar", "employeedesignation":"AGM", "departmentname" : "HUDA", "mobilenumber": "9466902763", "emailid" : "shaurya.kumar@gmail.com"},
                        {"id": "102","empname":"Rohan", "employeedesignation":"STM", "departmentname" : "HAFED", "mobilenumber": "9466906427", "emailid" : "rohan.kumar@yahoo.com"}];

        // vm.addtags = [{"id": "10","name": "Noting"},{"id": "11","name": "DO"},{"id": "15","name": "General Note"}];
        // console.log(vm.tags);


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
