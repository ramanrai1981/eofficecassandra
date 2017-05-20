(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .controller('FileController', FileController);

    FileController.$inject = ['File'];

    function FileController(File) {

        var vm = this;

        vm.files = [];

        loadAll();

        function loadAll() {
            File.query(function(result) {
                vm.files = result;
                vm.searchQuery = null;
            });
        }
    }
})();
