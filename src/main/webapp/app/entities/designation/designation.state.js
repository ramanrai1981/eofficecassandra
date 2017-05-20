(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('designation', {
            parent: 'entity',
            url: '/designation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eofficeApp.designation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/designation/designations.html',
                    controller: 'DesignationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('designation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('designation-detail', {
            parent: 'designation',
            url: '/designation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eofficeApp.designation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/designation/designation-detail.html',
                    controller: 'DesignationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('designation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Designation', function($stateParams, Designation) {
                    return Designation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'designation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('designation-detail.edit', {
            parent: 'designation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/designation/designation-dialog.html',
                    controller: 'DesignationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Designation', function(Designation) {
                            return Designation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('designation.new', {
            parent: 'designation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/designation/designation-dialog.html',
                    controller: 'DesignationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                organisationid: null,
                                departmentid: null,
                                designation: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('designation', null, { reload: 'designation' });
                }, function() {
                    $state.go('designation');
                });
            }]
        })
        .state('designation.edit', {
            parent: 'designation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/designation/designation-dialog.html',
                    controller: 'DesignationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Designation', function(Designation) {
                            return Designation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('designation', null, { reload: 'designation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('designation.delete', {
            parent: 'designation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/designation/designation-delete-dialog.html',
                    controller: 'DesignationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Designation', function(Designation) {
                            return Designation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('designation', null, { reload: 'designation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
