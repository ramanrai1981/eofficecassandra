(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('AddemployeeController', AddemployeeController);

    AddemployeeController.$inject = ['Employeesupdated'];

    function AddemployeeController(Employeesupdated) {

        var vm = this;

        vm.addemployees = [];

        loadAll();

        function loadAll() {
            Employeesupdated.query(function(result) {
                vm.employees = result;
                vm.searchQuery = null;
            });
        }
    }
})();
