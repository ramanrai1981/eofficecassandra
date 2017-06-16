(function() {
    'use strict';
    angular
        .module('eofficeApp')
        .factory('Employeesupdated', Employeesupdated);

   Employeesupdated.$inject = ['$resource', 'DateUtils'];

    function Employeesupdated ($resource, DateUtils) {
        var resourceUrl =  'api/employeesupdated/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateofbirth = DateUtils.convertDateTimeFromServer(data.dateofbirth);
                        data.dateofjoining = DateUtils.convertDateTimeFromServer(data.dateofjoining);
                        data.relievingdate = DateUtils.convertDateTimeFromServer(data.relievingdate);
                        data.createdate = DateUtils.convertDateTimeFromServer(data.createdate);
                        data.updatedate = DateUtils.convertDateTimeFromServer(data.updatedate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
