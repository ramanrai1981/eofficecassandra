(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('AddemployeeController', AddemployeeController);

    AddemployeeController.$inject = ['Addemployee'];

    function AddemployeeController(Addemployee) {

        var vm = this;

        vm.addemployees = [];

        loadAll();

        function loadAll() {
            Addemployee.query(function(result) {
                vm.employees = result;
                vm.searchQuery = null;
            });
        }
    }
})();
