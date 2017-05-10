(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('clearedfile', {
            parent: 'app',
            url: '/clearedfile',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/clearedfile/clearedfile.html',
                    controller: 'ClearedfileController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('clearedfile');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
