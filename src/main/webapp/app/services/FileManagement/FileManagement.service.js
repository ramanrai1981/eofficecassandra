(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .factory('FileManagement',['$http', function($http){
        return {
                saveFile: function(file,filename){
                    var resourceUrl =  'api/FileManagement';
                    var fd = new FormData();
                    fd.append('file', file);
                    fd.append('filename',filename);
                    $http.post(resourceUrl, fd, {
                    transformRequest : angular.identity,
                    headers : {
                    'Content-Type' : undefined
                    }
                    }).success(function() {
                    console.log('success');
                    }).error(function() {
                    console.log('error');
                    });
                }
            }
        }]);
})();

