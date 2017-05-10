(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('pendingfile', {
            parent: 'app',
            url: '/pendingfile',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/pendingfile/pendingfile.html',
                    controller: 'PendingfileController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('pendingfile');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
