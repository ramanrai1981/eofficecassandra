(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('FileMovementDetailController', FileMovementDetailController);

    FileMovementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FileMovement'];

    function FileMovementDetailController($scope, $rootScope, $stateParams, previousState, entity, FileMovement) {
        var vm = this;

        vm.fileMovement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eofficeApp:fileMovementUpdate', function(event, result) {
            vm.fileMovement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
