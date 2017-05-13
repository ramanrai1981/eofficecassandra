(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('OrganisationDialogController', OrganisationDialogController);

    OrganisationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Organisation'];

    function OrganisationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Organisation) {
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
                Organisation.update(vm.organisation, onSaveSuccess, onSaveError);
            } else {
                Organisation.save(vm.organisation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eofficeApp:organisationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.establishmentyear = false;
        vm.datePickerOpenStatus.createdate = false;
        vm.datePickerOpenStatus.updatedate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
