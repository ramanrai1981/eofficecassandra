(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('file-log', {
            parent: 'entity',
            url: '/file-log',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eofficeApp.fileLog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-log/file-logs.html',
                    controller: 'FileLogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fileLog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('file-log-detail', {
            parent: 'file-log',
            url: '/file-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eofficeApp.fileLog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-log/file-log-detail.html',
                    controller: 'FileLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fileLog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FileLog', function($stateParams, FileLog) {
                    return FileLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'file-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('file-log-detail.edit', {
            parent: 'file-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-log/file-log-dialog.html',
                    controller: 'FileLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileLog', function(FileLog) {
                            return FileLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-log.new', {
            parent: 'file-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-log/file-log-dialog.html',
                    controller: 'FileLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fileNo: null,
                                title: null,
                                markFrom: null,
                                markTo: null,
                                markDate: null,
                                updateDate: null,
                                comment: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('file-log', null, { reload: 'file-log' });
                }, function() {
                    $state.go('file-log');
                });
            }]
        })
        .state('file-log.edit', {
            parent: 'file-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-log/file-log-dialog.html',
                    controller: 'FileLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileLog', function(FileLog) {
                            return FileLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-log', null, { reload: 'file-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-log.delete', {
            parent: 'file-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-log/file-log-delete-dialog.html',
                    controller: 'FileLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FileLog', function(FileLog) {
                            return FileLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-log', null, { reload: 'file-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
