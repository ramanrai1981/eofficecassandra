(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('OrganisationController', OrganisationController);

    OrganisationController.$inject = ['Organisation', 'OrganisationSearch'];

    function OrganisationController(Organisation, OrganisationSearch) {

        var vm = this;

        vm.organisations = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Organisation.query(function(result) {
                vm.organisations = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            OrganisationSearch.query({query: vm.searchQuery}, function(result) {
                vm.organisations = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
