(function() {
    'use strict';
    angular
        .module('eofficeApp')
        .factory('Organisation', Organisation);

    Organisation.$inject = ['$resource', 'DateUtils'];

    function Organisation ($resource, DateUtils) {
        var resourceUrl =  'api/organisations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdate = DateUtils.convertDateTimeFromServer(data.createdate);
                        data.updatedate = DateUtils.convertDateTimeFromServer(data.updatedate);
                        data.establishmentdate = DateUtils.convertDateTimeFromServer(data.establishmentdate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
