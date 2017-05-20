(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('DesignationController', DesignationController);

    DesignationController.$inject = ['Designation'];

    function DesignationController(Designation) {

        var vm = this;

        vm.designations = [];

        loadAll();

        function loadAll() {
            Designation.query(function(result) {
                vm.designations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
