(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('FileLogDeleteController',FileLogDeleteController);

    FileLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'FileLog'];

    function FileLogDeleteController($uibModalInstance, entity, FileLog) {
        var vm = this;

        vm.fileLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FileLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
