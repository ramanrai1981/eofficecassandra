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
            }],

            file: function () {
                    return {
                        fileNo: null,
                        title: null,
                        tag: null,
                        uploadDate: null,
                        status: true,
                        id: null
                    };
            },

            filemovement: function () {
                    return {
                        fileId: null,
                        markFrom: null,
                        markTo: null,
                        fileName: null,
                        markDate: null,
                        updateDate: null,
                        actionStatus: null,
                        comment: null,
                        id: null
                    };
            }

            }
        });
    }
})();
