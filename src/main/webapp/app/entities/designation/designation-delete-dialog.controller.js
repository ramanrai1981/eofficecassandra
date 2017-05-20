(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('DesignationDeleteController',DesignationDeleteController);

    DesignationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Designation'];

    function DesignationDeleteController($uibModalInstance, entity, Designation) {
        var vm = this;

        vm.designation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Designation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
