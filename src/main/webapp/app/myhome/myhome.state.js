(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('myhome', {
            parent: 'app',
            url: '/myhome',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/myhome/myhome.html',
                    controller: 'MyHomeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('myhome');
                    return $translate.refresh();
                }]
            }
//            organisation: function () {
//                return {
//                    orgname: null,
//                    hod: null,
//                    address: null,
//                    establishmentyear: null,
//                    active: null,
//                    id: null
//                };
//            }
        });
    }
})();
