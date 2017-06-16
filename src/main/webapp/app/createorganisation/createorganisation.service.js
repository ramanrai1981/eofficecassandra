(function() {
    'use strict';
    angular
        .module('eofficeApp')
        .factory('Organisationsupdated', Organisationsupdated);

    Organisationsupdated.$inject = ['$resource', 'DateUtils'];

    function Organisationsupdated ($resource, DateUtils) {
        var resourceUrl =  'api/organisationsupdated/:id';

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
