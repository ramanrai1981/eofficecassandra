(function() {
    'use strict';
    angular
        .module('eofficeApp')
        .factory('FileLog', FileLog);

    FileLog.$inject = ['$resource', 'DateUtils'];

    function FileLog ($resource, DateUtils) {
        var resourceUrl =  'api/file-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.markDate = DateUtils.convertDateTimeFromServer(data.markDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
