(function() {
    'use strict';

    angular
        .module('eofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customsearch', {
            parent: 'app',
            url: '/customsearch',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/customsearch/customsearch.html',
                    controller: 'CustomsearchController',
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
                    $translatePartialLoader.addPart('customsearch');
                    return $translate.refresh();
                }]
            }
        })

        .state('customsearch-markto-user', {
            parent: 'customsearch',
            url: '/customsearch-markto-user/{fileid}',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/customsearch/customsearch-markto-user.html',
                    controller: 'CustomsearchmarktoController',
                    controllerAs: 'vm'
                }
            },

            employee: function () {
                return {
                    empid: null,
                    empname: null,
                    department: null,
                    designation: null,
                    emailid: null,
                    dateofbirth: null,
                    dateofjoining: null,
                    relievingdate: null,
                    active: null,
                    createdate: null,
                    updatedate: null,
                    mobilenumber: null,
                    id: null
                };
            },

            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('customsearch');
                    return $translate.refresh();
                }],
                selectedfiletomark: ['File','$stateParams', function(File,$stateParams) {
                    return File.get({id : $stateParams.fileid}).$promise;
                }]

            }
        })

    }
})();
