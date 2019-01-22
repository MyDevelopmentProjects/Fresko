Array.prototype.remove = function(from, to) {
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    return this.push.apply(this, rest);
};

(function () {
    'use strict';
    angular
        .module('app', [
            'ngAnimate',
            'ngResource',
            'ngSanitize',
            'ngStorage',
            'ngStore',
            'ngCookies',
            'ui.router',
            'ui.utils',
            'ui.load',
            'ui.jp',
            'oc.lazyLoad',
            'ui.bootstrap',
            'summernote',
            'pascalprecht.translate', // translateProvider
            'angularFileUpload',
            'angular-jwt.userprofile',
            'angular-jwt.options',
            'angular-jwt.interceptor',
            'angular-jwt.jwt',
            'angular-jwt.authManager'
        ]);
})();

(function () {
    'use strict';
    angular
        .module('app')
        .controller('AppCtrl', AppCtrl)
    AppCtrl.$inject = ['$scope', '$http', '$localStorage', '$location', '$rootScope', '$anchorScroll', '$timeout', '$window', '$cacheFactory', '$state', 'authManager', 'urls'];
    function AppCtrl($scope, $http, $localStorage, $location, $rootScope, $anchorScroll, $timeout, $window, $cacheFactory, $state, authManager, urls) {
        var vm = $scope;
        vm.isIE = isIE();
        vm.isSmart = isSmart();
        // config
        vm.currentTitle = 'დეშბორდი';
        vm.app = {
            name: 'YTS',
            version: '1.0.0',
            // for chart colors
            color: {
                'primary': '#dd5220',
                'accent': '#dd5220',
                'warn': '#fcc100',
                'info': '#6887ff',
                'success': '#6cc788',
                'warning': '#f77a99',
                'danger': '#f44455',
                'white': '#ffffff',
                'light': '#f1f2f3',
                'dark': '#2e3e4e',
                'black': '#2a2b3c'
            },
            setting: {
                theme: {
                    primary: 'primary',
                    accent: 'accent',
                    warn: 'warn'
                },
                folded: false,
                boxed: false,
                container: false,
                themeID: 1,
                bg: ''
            },
            topMenuItems: [],
            fabMenuItems: [],
            outerScope: {},
            fabScope: {},
            evalFunc: function (i) {
                var item = this.outerScope.topMenuItems[i];
                item.callback(item.param);
            },
            evalFabFunc: function (i) {
                var item = this.fabScope.fabMenuItems[i];
                item.callback(item.param);
            }
        };

        $scope.logout = function(){
            authManager.unauthenticate();
        };

        $scope.invalidateCache = function () {
            $cacheFactory.get('$http').removeAll();
        };


        var setting = vm.app.name + '-Setting';
        // save settings to local storage
        if (angular.isDefined($localStorage[setting])) {
            vm.app.setting = $localStorage[setting];
        } else {
            $localStorage[setting] = vm.app.setting;
        }
        // watch changes
        $scope.$watch('app.setting', function () {
            $localStorage[setting] = vm.app.setting;
        }, true);

        getParams('bg') && (vm.app.setting.bg = getParams('bg'));

        vm.setTheme = setTheme;
        setColor();

        function setTheme(theme) {
            vm.app.setting.theme = theme.theme;
            setColor();
            if (theme.url) {
                $timeout(function () {
                    $window.location.href = theme.url;
                }, 100, false);
            }
        }

        function setColor() {
            vm.app.setting.color = {
                primary: getColor(vm.app.setting.theme.primary),
                accent: getColor(vm.app.setting.theme.accent),
                warn: getColor(vm.app.setting.theme.warn)
            };
        }

        function getColor(name) {
            return vm.app.color[name] ? vm.app.color[name] : palette.find(name);
        }

        $rootScope.$on('$stateChangeSuccess', openPage);

        function openPage() {
            // goto top
            $('.top-menu-items').hide();
            $location.hash('content');
            $anchorScroll();
            $location.hash('');
            // hide open menu
            $('#aside').modal('hide');
            $('body').removeClass('modal-open').find('.modal-backdrop').remove();
            $('.navbar-toggleable-sm').collapse('hide');

            if ($state.current && $state.current.data.title) {
                $('#pageTitle').text($state.current.data.title)
            }
        }

        vm.goBack = function () {
            $window.history.back();
        };

        function isIE() {
            return !!navigator.userAgent.match(/MSIE/i) || !!navigator.userAgent.match(/Trident.*rv:11\./);
        }

        function isSmart() {
            // Adapted from http://www.detectmobilebrowsers.com
            var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
            // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
            return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
        }

        function getParams(name) {
            name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
            var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
            return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
        }


    }
})();