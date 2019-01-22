angular.module('app').controller('GalleryController',
    ['$rootScope', '$scope', '$http', 'GridManager', 'ModalManager', 'FileUploader',
        function ($rootScope, $scope, $http, GridManager, ModalManager, FileUploader) {

            angular.extend($scope, {
                url: '/gallery/list',
                saveURL: '/gallery/put',
                deleteURL: '/gallery/delete',
                init: {}
            });

            GridManager.givePowerTo($scope);
            ModalManager.enableModals($scope);
            $scope.AmfTable.openPage(0);


            $scope.AmfTable.showAddEdit = function(item) {

                $scope.init.action = item ? 'რედაქტირება' : 'დამატება';
                $scope.object = {};
                if (item) {
                    $scope.object = angular.copy(item);
                    $scope.object.images = JSON.parse($scope.object.images)
                } else {
                    $scope.object.images = [];
                }
                $('#showAddEdit').modal('show');
            };

            $scope.uploader.onCompleteItem = function (fileItem, response, status, headers) {
                if (response && response.length > 0) {
                    $scope.object.images.push(response[0].name)
                }
            };

            $scope.uploader.onCompleteAll = function () {
                $("input[type='file']").val('').clone(true);
                $scope.AmfTable.save()
                $scope.uploader.queue = [];
            };

            $scope.AmfTable.save = function () {
                $scope.object.images = JSON.stringify($scope.object.images)
                $http.post($scope.saveURL, $scope.object).success(function (resp) {
                    $scope.AmfTable.reloadData()
                    $('#showAddEdit').modal('hide');
                })
            }


        }]);