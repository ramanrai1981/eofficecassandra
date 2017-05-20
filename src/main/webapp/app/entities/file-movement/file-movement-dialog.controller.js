(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('FileMovementDialogController', FileMovementDialogController);

    FileMovementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FileMovement'];

    function FileMovementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FileMovement) {
        var vm = this;

        vm.fileMovement = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fileMovement.id !== null) {
                FileMovement.update(vm.fileMovement, onSaveSuccess, onSaveError);
            } else {
                FileMovement.save(vm.fileMovement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eofficeApp:fileMovementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.markDate = false;
        vm.datePickerOpenStatus.updateDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
