(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('adddepartment', {
            parent: 'app',
            url: '/adddepartment',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/adddepartment/adddepartment.html',
                    controller: 'AdddepartmentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('adddepartment');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
