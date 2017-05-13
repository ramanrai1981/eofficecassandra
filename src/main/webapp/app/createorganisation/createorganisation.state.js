(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('createorganisation', {
            parent: 'entity',
            url: '/createorganisations',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eofficeApp.createorganisations.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/createorganisation/createorganisation-dialog.html',
                    controller: 'CreateorganisationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('createorganisation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })

        .state('createorganisation.new', {
            parent: 'home',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/createorganisation/createorganisation-dialog.html',
                    controller: 'CreateorganisationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orgname: null,
                                hod: null,
                                address: null,
                                establishmentyear: null,
                                active: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('home', null, { reload: 'home' });
                }, function() {
                    $state.go('home');
                });
            }]
        })

    }

})();
