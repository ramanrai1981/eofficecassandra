(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('CreateorganisationController', CreateorganisationController);

    CreateorganisationController.$inject = ['Organisationsupdated'];

    function CreateorganisationController(Organisationsupdated) {

        var vm = this;

        vm.createorganisations = [];

        loadAll();

        function loadAll() {
            Organisationsupdated.query(function(result) {
                vm.createorganisations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
