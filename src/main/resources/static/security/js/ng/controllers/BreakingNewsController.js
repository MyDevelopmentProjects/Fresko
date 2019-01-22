angular.module('app').controller('BreakingNewsController',
    ['$rootScope', '$scope', '$http', 'GridManager', 'ModalManager', function ($rootScope, $scope, $http, GridManager, ModalManager) {

        angular.extend($scope, {
            url: '/breakingNews/list',
            saveURL: '/breakingNews/put',
            deleteURL: '/breakingNews/delete',
            init: {}
        });

        GridManager.givePowerTo($scope);
        ModalManager.enableModals($scope);
        $scope.AmfTable.openPage(0);

        setTimeout(function(){
            $('.form_datetime').datetimepicker({
                language:  'ka',
                weekStart: 1,
                todayBtn:  1,
                autoclose: 1,
                todayHighlight: 1,
                startView: 2,
                forceParse: 0,
                showMeridian: 1
            });
        }, 1500)
    }]);