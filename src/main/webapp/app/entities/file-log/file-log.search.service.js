(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .factory('FileLogSearch', FileLogSearch);

    FileLogSearch.$inject = ['$resource'];

    function FileLogSearch($resource) {
        var resourceUrl =  'api/_search/file-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
