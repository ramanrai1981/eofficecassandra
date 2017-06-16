(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('CreateorganisationDialogController', CreateorganisationDialogController);

    CreateorganisationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Organisationsupdated'];

    function CreateorganisationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Organisationsupdated) {
        var vm = this;

        vm.organisation = entity;
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
            if (vm.organisation.id !== null) {
                Organisationsupdated.update(vm.organisation, onSaveSuccess, onSaveError);
            } else {
                Organisationsupdated.save(vm.organisation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eofficeApp:createorganisationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdate = false;
        vm.datePickerOpenStatus.updatedate = false;
        vm.datePickerOpenStatus.establishmentdate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
