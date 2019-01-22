angular.module('app').controller('SliderController',
    ['$rootScope', '$scope', '$http', 'GridManager', 'ModalManager', 'FileUploader',
        function ($rootScope, $scope, $http, GridManager, ModalManager, FileUploader) {

            var vm = $scope;

            angular.extend(vm, {
                url: '/slider/list',
                categoryURL: '/sliderCategory/list?size=100',
                saveURL: '/slider/put',
                deleteURL: '/slider/delete',
                init: {},
                categories: []
            });

            GridManager.givePowerTo(vm);
            ModalManager.enableModals(vm);
            vm.AmfTable.openPage(0);

            vm.getCategories = getCategories;

            vm.uploader.onCompleteItem = function (fileItem, response, status, headers) {
                if (response && response.length > 0) {
                    $scope.object.imgUrl = response[0]
                }
            };

            vm.AmfTable.save = function () {
                var objectCopy = angular.copy(vm.object);
                objectCopy.createdBy = {id: 1};
                objectCopy.category = {id: 1};
                objectCopy.orderNum = 0;
                objectCopy.active = true;

                if(vm.object.imgUrl && typeof vm.object.imgUrl === 'object') {
                    objectCopy.imgUrl = vm.object.imgUrl.name
                }


                $http.post(vm.saveURL, objectCopy).success(function (response) {
                    if (!response.success) {
                        vm.showErrorModal(response.errorObj);
                        return;
                    }

                    $http.post(vm.saveURL, objectCopy).success(function (response) {
                        if (!response.success) {
                            vm.showErrorModal(response.errorObj);
                            return;
                        }
                        vm.showSuccessAlert("Success");
                        vm.AmfTable.reloadData();
                        $('#showAddEdit').modal('hide');
                    });

                });
            };

            $scope.isFileSet = function() {
                return $scope.object.imgUrl !== undefined && $scope.object.imgUrl !== null && $scope.object.imgUrl.length > 0
            };

            function getCategories() {
                $http.get(vm.categoryURL, {cache: true}).success(function (resp) {
                    if (resp.content)
                        vm.categories = resp.content;
                    else vm.categories = [];
                })
            }


            vm.getCategories();

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
