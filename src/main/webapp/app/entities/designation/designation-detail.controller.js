(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('DesignationDetailController', DesignationDetailController);

    DesignationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Designation'];

    function DesignationDetailController($scope, $rootScope, $stateParams, previousState, entity, Designation) {
        var vm = this;

        vm.designation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eofficeApp:designationUpdate', function(event, result) {
            vm.designation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
