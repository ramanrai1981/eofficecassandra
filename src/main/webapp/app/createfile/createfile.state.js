(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('createfile', {
            parent: 'app',
            url: '/createfile',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/createfile/createfile.html',
                    controller: 'CreatefileController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('createfile');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
