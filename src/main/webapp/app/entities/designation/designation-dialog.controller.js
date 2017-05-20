(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('DesignationDialogController', DesignationDialogController);

    DesignationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Designation'];

    function DesignationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Designation) {
        var vm = this;

        vm.designation = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.designation.id !== null) {
                Designation.update(vm.designation, onSaveSuccess, onSaveError);
            } else {
                Designation.save(vm.designation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eofficeApp:designationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
