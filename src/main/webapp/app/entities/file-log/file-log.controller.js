(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('FileLogController', FileLogController);

    FileLogController.$inject = ['FileLog'];

    function FileLogController(FileLog) {

        var vm = this;

        vm.fileLogs = [];

        loadAll();

        function loadAll() {
            FileLog.query(function(result) {
                vm.fileLogs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
