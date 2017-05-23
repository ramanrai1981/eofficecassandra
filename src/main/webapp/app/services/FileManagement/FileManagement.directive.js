(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .directive('fileModel', ['$parse', function ($parse) {
                return {
                    restrict: 'A',
                    link: function(scope, element, attrs) {
                        var model = $parse(attrs.fileModel);
                        var modelSetter = model.assign;
                        var maxSize = 10*1024*1024; //10 MB

                        element.bind('change', function(){
                            scope.$apply(function(){
                            scope.file.maxSizeError = false;

                            if (element[0].files.length > 1) {
                                modelSetter(scope, element[0].files);
                                } else {
                                modelSetter(scope, element[0].files[0]);
                                }
                            var fileSize = element[0].files[0].size;
                            if (fileSize > maxSize) {
                                scope.file.maxSizeError = true;
                            }


                            });
                        });
                    }
                };
            }]);
})();
