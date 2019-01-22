(function () {
    'use strict';
    angular
        .module('app')
        .run(runBlock)
        .constant('urls', {
            BASE_API: '/'
        })
        .config(config);

    runBlock.$inject = ['$rootScope', '$state', '$stateParams', '$translate', '$location', '$localStorage', 'authManager'];
    function runBlock($rootScope, $state, $stateParams, $translate, $location, $localStorage, authManager) {
        authManager.checkAuthOnRefresh();
        authManager.redirectWhenUnauthenticated();
    }

    config.$inject = ['$httpProvider', '$stateProvider', '$translateProvider', '$urlRouterProvider', 'jwtOptionsProvider', 'MODULE_CONFIG'];
    function config($httpProvider, $stateProvider, $translateProvider, $urlRouterProvider, jwtOptionsProvider, MODULE_CONFIG) {


        // Injecting JWT Interceptor
        jwtOptionsProvider.config({
            authenticationSuccessRedirector: ['$state', function ($state) {
                $state.go('app.dashboard');
            }],
            unauthenticatedRedirector: ['$state', function ($state) {
                $state.go('app.signin');
            }],
            tokenGetter: ['$localStorage', function ($localStorage) {
                return $localStorage.token;
            }]
        });

        $httpProvider.interceptors.push('jwtInterceptor');

        $urlRouterProvider.otherwise(function ($injector) {
            var $state = $injector.get("$state");
            $state.go("app.signin");
        });

        $stateProvider
            .state('app', {
                abstract: true,
                url: '/app',
                views: {
                    '': {
                        templateUrl: '/security/view/Default/aside.html'
                    }
                },
                controller: 'BaseController',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/BaseController.js"
                            ]
                        });
                    }
                }
            })

            .state('app.dashboard', {
                url: '/dashboard',
                templateUrl: '/security/view/Default/index.html',
                data: {
                    title: 'მთავარი გვერდი',
                    requiresLogin: true
                },
                controller: 'DefaultController',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/DefaultController.js"
                            ]
                        });
                    }
                }
            })
            .state('app.signin', {
                url: '/signin',
                templateUrl: '/security/view/Signin/index.html',
                data: {
                    title: 'ავტორიზაცია',
                    requiresUnauthenticated: true
                },
                controller: 'SignInController',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/SignInController.js"
                            ]
                        });
                    }
                }
            })
            .state('app.sliders.category', {
                url: '/category',
                templateUrl: '/security/view/Slider/category.html',
                data: {
                    title: 'სლაიდერის კატეგორია',
                    requiresLogin: true
                },
                controller: 'SliderCategoryController',
                resolve: {
                    userProfile: "UserProfile",
                    access: ["Access", function (Access) {
                        return Access.hasRole("SUPER_ADMIN");
                    }],
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/SliderCategoryController.js"
                            ]
                        });
                    }
                }
            })
            .state('app.sliders.list', {
                url: '/list',
                templateUrl: '/security/view/Slider/list.html',
                data: {
                    title: 'სლაიდების სია',
                    requiresLogin: true
                },
                controller: 'SliderController',
                resolve: {
                    userProfile: "UserProfile",
                    access: ["Access", function (Access) {
                        return Access.hasRole("SUPER_ADMIN");
                    }],
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/SliderController.js"
                            ]
                        });
                    }
                }
            })
            .state('app.sections.about', {
                url: '/about',
                templateUrl: '/security/view/About/list.html',
                data: {
                    title: 'ჩანაწერების სია',
                    requiresLogin: true
                },
                controller: 'AboutController',
                resolve: {
                    userProfile: "UserProfile",
                    access: ["Access", function (Access) {
                        return Access.hasRole("SUPER_ADMIN");
                    }],
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/AboutController.js"
                            ]
                        });
                    }
                }
            })
            .state('app.gallery.list', {
                url: '/list',
                templateUrl: '/security/view/Gallery/index.html',
                data: {
                    title: 'გალერეა',
                    requiresLogin: true
                },
                controller: 'GalleryController',
                resolve: {
                    userProfile: "UserProfile",
                    access: ["Access", function (Access) {
                        return Access.hasRole("SUPER_ADMIN");
                    }],
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/GalleryController.js"
                            ]
                        });
                    }
                }
            })
            .state('app.sections.list', {
                url: '/list',
                templateUrl: '/security/view/Section/list.html',
                data: {
                    title: 'სექციების სია',
                    requiresLogin: true
                },
                controller: 'SectionController',
                resolve: {
                    userProfile: "UserProfile",
                    access: ["Access", function (Access) {
                        return Access.hasRole("SUPER_ADMIN");
                    }],
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/SectionController.js"
                            ]
                        });
                    }
                }
            })
            .state('app.sliders.breakingnews', {
                url: '/breakingnews',
                templateUrl: '/security/view/Slider/breakingnews.html',
                data: {
                    title: 'ცხელი ნიუსები',
                    requiresLogin: true
                },
                controller: 'BreakingNewsController',
                resolve: {
                    userProfile: "UserProfile",
                    access: ["Access", function (Access) {
                        return Access.hasRole("SUPER_ADMIN");
                    }],
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/BreakingNewsController.js"
                            ]
                        });
                    }
                }
            })


            .state('app.posts.category', {
                url: '/category',
                templateUrl: '/security/view/Post/category.html',
                data: {
                    title: 'პოსტების კატეგორია',
                    requiresLogin: true
                },
                controller: 'PostCategoryController',
                resolve: {
                    userProfile: "UserProfile",
                    access: ["Access", function (Access) {
                        return Access.hasRole("SUPER_ADMIN");
                    }],
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/PostCategoryController.js"
                            ]
                        });
                    }
                }
            })
            .state('app.posts.list', {
                url: '/list',
                templateUrl: '/security/view/Post/list.html',
                data: {
                    title: 'პოსტების სია',
                    requiresLogin: true
                },
                controller: 'PostController',
                resolve: {
                    userProfile: "UserProfile",
                    access: ["Access", function (Access) {
                        return Access.hasRole("SUPER_ADMIN");
                    }],
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/PostController.js"
                            ]
                        });
                    }
                }
            })
            .state('app.products.list', {
                url: '/list',
                templateUrl: '/security/view/Products/list.html',
                data: {
                    title: 'პროდუქტების სია',
                    requiresLogin: true
                },
                controller: 'ProductsController',
                resolve: {
                    userProfile: "UserProfile",
                    access: ["Access", function (Access) {
                        return Access.hasRole("SUPER_ADMIN");
                    }],
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: "app",
                            files: [
                                "/security/js/ng/controllers/ProductsController.js"
                            ]
                        });
                    }
                }
            })


            .state('app.user.list', {
                url: '/list',
                templateUrl: '/security/view/User/index.html',
                data: {
                    title: 'მომხმარებლები',
                    requiresLogin: true
                },
                controller: 'UserController',
                resolve: load([
                    "/security/js/ng/controllers/UserController.js", "ui.select"
                ])
            })

            .state('app.quicklinks.list', {
                url: '/list',
                templateUrl: '/security/view/QuickLinks/list.html',
                data: {
                    title: 'სწრაფი ბმულები',
                    requiresLogin: true
                },
                controller: 'QuickLinksController',
                resolve: load(["/security/js/ng/controllers/QuickLinksController.js"])
            })


            .state('app.sliders', {
                url: '/slider',
                template: '<div ui-view></div>'
            })
            .state('app.gallery', {
                url: '/gallery',
                template: '<div ui-view></div>'
            })
            .state('app.posts', {
                url: '/post',
                template: '<div ui-view></div>'
            })
            .state('app.products', {
                url: '/products',
                template: '<div ui-view></div>'
            })
            .state('app.tags', {
                url: '/tags',
                template: '<div ui-view></div>'
            })
            .state('app.sections', {
                url: '/sections',
                template: '<div ui-view></div>'
            })
            .state('app.quicklinks', {
                url: '/quickLinks',
                template: '<div ui-view></div>'
            })
            // main state
            .state('app.main', {
                url: '/main',
                template: '<div ui-view></div>'
            })
            // user state
            .state('app.user', {
                url: '/user',
                template: '<div ui-view></div>'
            })
            // ui router
            .state('app.layout', {
                url: '/layout',
                template: '<div ui-view></div>'
            })
            .state('app.ui', {
                url: '/ui',
                template: '<div ui-view></div>'
            })
            .state('app.form', {
                url: '/form',
                template: '<div ui-view></div>'
            })
            .state('app.table', {
                url: '/table',
                template: '<div ui-view></div>'
            })
            .state('access', {
                url: '/access',
                template: '<div class="dark bg-auto w-full"><div ui-view class="fade-in-right-big smooth pos-rlt"></div></div>'
            });


        function load(srcs, callback) {
            return {
                deps: ['$ocLazyLoad', '$q',
                    function ($ocLazyLoad, $q) {
                        var deferred = $q.defer();
                        var promise = false;
                        srcs = angular.isArray(srcs) ? srcs : srcs.split(/\s+/);
                        if (!promise) {
                            promise = deferred.promise;
                        }
                        angular.forEach(srcs, function (src) {
                            promise = promise.then(function () {
                                angular.forEach(MODULE_CONFIG, function (module) {
                                    if (module.name == src) {
                                        src = module.module ? module.name : module.files;
                                    }
                                });
                                return $ocLazyLoad.load(src);
                            });
                        });
                        deferred.resolve();
                        return callback ? promise.then(function () {
                            return callback();
                        }) : promise;
                    }]
            }
        }
    }
})();
