(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('OrganisationDeleteController',OrganisationDeleteController);

    OrganisationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Organisation'];

    function OrganisationDeleteController($uibModalInstance, entity, Organisation) {
        var vm = this;

        vm.organisation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Organisation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
