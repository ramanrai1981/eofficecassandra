(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('file-movement', {
            parent: 'entity',
            url: '/file-movement',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eofficeApp.fileMovement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-movement/file-movements.html',
                    controller: 'FileMovementController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fileMovement');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('file-movement-detail', {
            parent: 'file-movement',
            url: '/file-movement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eofficeApp.fileMovement.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-movement/file-movement-detail.html',
                    controller: 'FileMovementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fileMovement');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FileMovement', function($stateParams, FileMovement) {
                    return FileMovement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'file-movement',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('file-movement-detail.edit', {
            parent: 'file-movement-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-movement/file-movement-dialog.html',
                    controller: 'FileMovementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileMovement', function(FileMovement) {
                            return FileMovement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-movement.new', {
            parent: 'file-movement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-movement/file-movement-dialog.html',
                    controller: 'FileMovementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
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
                }).result.then(function() {
                    $state.go('file-movement', null, { reload: 'file-movement' });
                }, function() {
                    $state.go('file-movement');
                });
            }]
        })
        .state('file-movement.edit', {
            parent: 'file-movement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-movement/file-movement-dialog.html',
                    controller: 'FileMovementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileMovement', function(FileMovement) {
                            return FileMovement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-movement', null, { reload: 'file-movement' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-movement.delete', {
            parent: 'file-movement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-movement/file-movement-delete-dialog.html',
                    controller: 'FileMovementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FileMovement', function(FileMovement) {
                            return FileMovement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-movement', null, { reload: 'file-movement' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
