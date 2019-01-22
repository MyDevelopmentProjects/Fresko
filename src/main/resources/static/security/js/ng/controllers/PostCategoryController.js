angular.module('app').controller('PostCategoryController',
    ['$rootScope', '$scope', '$http', 'GridManager', 'ModalManager', function ($rootScope, $scope, $http, GridManager, ModalManager) {

        angular.extend($scope, {
            url: '/postCategory/list',
            saveURL: '/postCategory/put',
            deleteURL: '/postCategory/delete',
            init: {}
        });

        GridManager.givePowerTo($scope);
        ModalManager.enableModals($scope);
        $scope.AmfTable.openPage(0);


    }]);
