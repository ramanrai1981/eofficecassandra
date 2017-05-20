(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('FileMovementController', FileMovementController);

    FileMovementController.$inject = ['FileMovement'];

    function FileMovementController(FileMovement) {

        var vm = this;

        vm.fileMovements = [];

        loadAll();

        function loadAll() {
            FileMovement.query(function(result) {
                vm.fileMovements = result;
                vm.searchQuery = null;
            });
        }
    }
})();
