(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('FileMovementDeleteController',FileMovementDeleteController);

    FileMovementDeleteController.$inject = ['$uibModalInstance', 'entity', 'FileMovement'];

    function FileMovementDeleteController($uibModalInstance, entity, FileMovement) {
        var vm = this;

        vm.fileMovement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FileMovement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
