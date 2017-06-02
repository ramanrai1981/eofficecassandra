(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .factory('FileMovementSearch', FileMovementSearch);

    FileMovementSearch.$inject = ['$resource'];

    function FileMovementSearch($resource) {
        var resourceUrl =  'api/_search/file-movements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
