(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('FileLogDialogController', FileLogDialogController);

    FileLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FileLog'];

    function FileLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FileLog) {
        var vm = this;

        vm.fileLog = entity;
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
            if (vm.fileLog.id !== null) {
                FileLog.update(vm.fileLog, onSaveSuccess, onSaveError);
            } else {
                FileLog.save(vm.fileLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eofficeApp:fileLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.markDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
