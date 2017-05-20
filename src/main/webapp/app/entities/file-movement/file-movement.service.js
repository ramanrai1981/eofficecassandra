(function() {
    'use strict';
    angular
        .module('eofficeApp')
        .factory('FileMovement', FileMovement);

    FileMovement.$inject = ['$resource', 'DateUtils'];

    function FileMovement ($resource, DateUtils) {
        var resourceUrl =  'api/file-movements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.markDate = DateUtils.convertDateTimeFromServer(data.markDate);
                        data.updateDate = DateUtils.convertDateTimeFromServer(data.updateDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
