angular.module('app').controller('QuickLinksController',
    ['$rootScope', '$scope', '$http', 'GridManager', 'ModalManager', function ($rootScope, $scope, $http, GridManager, ModalManager) {

        angular.extend($scope, {
            url: '/quickLink/list',
            saveURL: '/quickLink/put',
            deleteURL: '/quickLink/delete',
            init: {}
        });

        GridManager.givePowerTo($scope);
        ModalManager.enableModals($scope);
        $scope.AmfTable.openPage(0);

    }]);