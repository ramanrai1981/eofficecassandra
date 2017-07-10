(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('addemployee', {
            parent: 'entity',
            url: '/addemployee',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eofficeApp.addemployees.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/addemployee/addemployee.html',
                    controller: 'AddemployeeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('addemployee');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })

        .state('addemployee.new', {
            parent: 'addemployee',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/addemployee/addemployee-dialog.html',
                    controller: 'AddemployeeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                empid: null,
                                empname: null,
                                department: null,
                                designation: null,
                                mobilenumber: null,
                                emailid: null,
                                dateofbirth: null,
                                dateofjoining: null,
                                relievingdate: null,
                                active: null,
                                createdate: null,
                                updatedate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('addemployee', null, { reload: 'addemployee' });
                }, function() {
                    $state.go('addemployee');
                });
            }]
        })

    }

})();
