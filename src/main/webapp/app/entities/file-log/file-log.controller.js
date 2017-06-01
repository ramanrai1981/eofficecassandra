(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('FileLogController', FileLogController);

    FileLogController.$inject = ['FileLog', 'FileLogSearch'];

    function FileLogController(FileLog, FileLogSearch) {

        var vm = this;

        vm.fileLogs = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            FileLog.query(function(result) {
                vm.fileLogs = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            FileLogSearch.query({query: vm.searchQuery}, function(result) {
                vm.fileLogs = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
