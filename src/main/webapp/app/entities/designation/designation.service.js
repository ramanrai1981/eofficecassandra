(function() {
    'use strict';
    angular
        .module('eofficeApp')
        .factory('Designation', Designation);

    Designation.$inject = ['$resource'];

    function Designation ($resource) {
        var resourceUrl =  'api/designations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
