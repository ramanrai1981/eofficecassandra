(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('OrganisationController', OrganisationController);

    OrganisationController.$inject = ['Organisation'];

    function OrganisationController(Organisation) {

        var vm = this;

        vm.organisations = [];

        loadAll();

        function loadAll() {
            Organisation.query(function(result) {
                vm.organisations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
