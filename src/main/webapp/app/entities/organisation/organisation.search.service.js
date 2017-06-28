(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .factory('OrganisationSearch', OrganisationSearch);

    OrganisationSearch.$inject = ['$resource'];

    function OrganisationSearch($resource) {
        var resourceUrl =  'api/_search/organisations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
