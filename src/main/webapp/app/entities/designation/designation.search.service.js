(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .factory('DesignationSearch', DesignationSearch);

    DesignationSearch.$inject = ['$resource'];

    function DesignationSearch($resource) {
        var resourceUrl =  'api/_search/designations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
