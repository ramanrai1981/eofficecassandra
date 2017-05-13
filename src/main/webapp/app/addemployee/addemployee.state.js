(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('addemployee', {
            parent: 'app',
            url: '/addemployee',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/addemployee/addemployee.html',
                    controller: 'AddemployeeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('addemployee');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
