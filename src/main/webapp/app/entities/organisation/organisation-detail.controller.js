(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('OrganisationDetailController', OrganisationDetailController);

    OrganisationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Organisation'];

    function OrganisationDetailController($scope, $rootScope, $stateParams, previousState, entity, Organisation) {
        var vm = this;

        vm.organisation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eofficeApp:organisationUpdate', function(event, result) {
            vm.organisation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
