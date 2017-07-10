(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('FileMovementController', FileMovementController);

    FileMovementController.$inject = ['FileMovement', 'FileMovementSearch'];

    function FileMovementController(FileMovement, FileMovementSearch) {

        var vm = this;

        vm.fileMovements = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            FileMovement.query(function(result) {
                vm.fileMovements = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            FileMovementSearch.query({query: vm.searchQuery}, function(result) {
                vm.fileMovements = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
