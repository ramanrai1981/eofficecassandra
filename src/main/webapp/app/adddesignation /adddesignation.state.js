(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('adddesignation', {
            parent: 'app',
            url: '/adddesignation',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/adddesignation/adddesignation.html',
                    controller: 'AdddesignationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('adddesignation');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
