(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('addorganisation', {
            parent: 'app',
            url: '/addorganisation',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/addorganisation/addorganisation.html',
                    controller: 'AddorganisationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('addorganisation');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
