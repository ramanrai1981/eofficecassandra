(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pendingfile', {
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

            file: function () {
                return {
                    fileNo: null,
                    title: null,
                    tag: null,
                    uploadDate: null,
                    status: false,
                    id: null
                };
            },

            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('pendingfile');
                    return $translate.refresh();
                }]
            }
        })

        .state('pendingfile-markto-user', {
            parent: 'pendingfile',
            url: '/pendingfile-markto-user/{fileid}',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/pendingfile/pendingfile-markto-user.html',
                    controller: 'PendingfilemarktoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('pendingfile');
                    return $translate.refresh();
                }],
                selectedfiletomark: ['File','$stateParams', function(File,$stateParams) {
                    return File.get({id : $stateParams.fileid}).$promise;
                }]

            }
        })

    }
})();
