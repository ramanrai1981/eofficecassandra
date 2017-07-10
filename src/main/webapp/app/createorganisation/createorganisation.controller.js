(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('CreateorganisationController', CreateorganisationController);

    CreateorganisationController.$inject = ['Createorganisation'];

    function CreateorganisationController(Createorganisation) {

        var vm = this;

        vm.createorganisations = [];

        loadAll();

        function loadAll() {
            Createorganisation.query(function(result) {
                vm.createorganisations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
