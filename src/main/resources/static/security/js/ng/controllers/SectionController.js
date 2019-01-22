angular.module('app').controller('SectionController',
    ['$rootScope', '$scope', '$http', 'GridManager', 'ModalManager', function ($rootScope, $scope, $http, GridManager, ModalManager) {

        angular.extend($scope, {
            url: '/section/list',
            saveURL: '/section/put',
            deleteURL: '/section/delete',
            init: {}
        });

        GridManager.givePowerTo($scope);
        ModalManager.enableModals($scope);
        $scope.AmfTable.openPage(0);

    }]);