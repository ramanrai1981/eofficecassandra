(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('organisation', {
            parent: 'entity',
            url: '/organisation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eofficeApp.organisation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/organisation/organisations.html',
                    controller: 'OrganisationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('organisation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('organisation-detail', {
            parent: 'organisation',
            url: '/organisation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eofficeApp.organisation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/organisation/organisation-detail.html',
                    controller: 'OrganisationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('organisation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Organisation', function($stateParams, Organisation) {
                    return Organisation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'organisation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('organisation-detail.edit', {
            parent: 'organisation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organisation/organisation-dialog.html',
                    controller: 'OrganisationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Organisation', function(Organisation) {
                            return Organisation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('organisation.new', {
            parent: 'organisation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organisation/organisation-dialog.html',
                    controller: 'OrganisationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orgname: null,
                                address: null,
                                active: null,
                                createdate: null,
                                updatedate: null,
                                owner: null,
                                establishmentdate: null,
                                createdby: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('organisation', null, { reload: 'organisation' });
                }, function() {
                    $state.go('organisation');
                });
            }]
        })
        .state('organisation.edit', {
            parent: 'organisation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organisation/organisation-dialog.html',
                    controller: 'OrganisationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Organisation', function(Organisation) {
                            return Organisation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('organisation', null, { reload: 'organisation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('organisation.delete', {
            parent: 'organisation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organisation/organisation-delete-dialog.html',
                    controller: 'OrganisationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Organisation', function(Organisation) {
                            return Organisation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('organisation', null, { reload: 'organisation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
