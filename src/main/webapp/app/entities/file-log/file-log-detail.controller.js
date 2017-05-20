(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('FileLogDetailController', FileLogDetailController);

    FileLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FileLog'];

    function FileLogDetailController($scope, $rootScope, $stateParams, previousState, entity, FileLog) {
        var vm = this;

        vm.fileLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eofficeApp:fileLogUpdate', function(event, result) {
            vm.fileLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
