(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('DesignationController', DesignationController);

    DesignationController.$inject = ['Designation', 'DesignationSearch'];

    function DesignationController(Designation, DesignationSearch) {

        var vm = this;

        vm.designations = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Designation.query(function(result) {
                vm.designations = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DesignationSearch.query({query: vm.searchQuery}, function(result) {
                vm.designations = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
