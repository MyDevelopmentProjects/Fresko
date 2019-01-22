angular.module('app').controller('SliderCategoryController',
    ['$rootScope', '$scope', '$http', 'GridManager', 'ModalManager', function ($rootScope, $scope, $http, GridManager, ModalManager) {

        angular.extend($scope, {
            url: '/sliderCategory/list',
            saveURL: '/sliderCategory/put',
            deleteURL: '/sliderCategory/delete',
            init: {}
        });

        GridManager.givePowerTo($scope);
        ModalManager.enableModals($scope);
        $scope.AmfTable.openPage(0);

    }]);
