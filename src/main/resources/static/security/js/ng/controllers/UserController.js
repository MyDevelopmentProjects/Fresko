angular.module('app').controller('UserController',
    ['$scope', '$http', '$localStorage', '$location', 'urls', 'GridManager', 'ModalManager',
        function ($scope, $http, $localStorage, $location, urls, GridManager, ModalManager) {


            angular.extend($scope, {
                url:'base-user/list',
                saveURL:'user/put',
                deleteURL:'user/delete',
                roleURL: 'role/all',
                role_list:[],
                multiSelect:{},
                init:{}
            });

            GridManager.givePowerTo($scope);
            ModalManager.enableModals($scope);
            $scope.AmfTable.openPage(0);

        }
    ]);
