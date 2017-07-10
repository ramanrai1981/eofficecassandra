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
            parent: 'myhome',
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
                                 address: null,
                                 active: null,
                                 owner: null,
                                 establishmentdate: null,
                                 id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('myhome', null, { reload: 'myhome' });
                }, function() {
                    $state.go('myhome');
                });
            }]
        })

    }

})();
