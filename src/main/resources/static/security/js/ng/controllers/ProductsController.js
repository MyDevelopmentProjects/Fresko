angular.module('app').controller('ProductsController',
    ['$rootScope', '$scope', '$http', 'GridManager', 'ModalManager', 'FileUploader', '$sce',
        function ($rootScope, $scope, $http, GridManager, ModalManager, FileUploader, $sce) {

            var vm = $scope;

            angular.extend(vm, {
                url: '/products/list',
                saveURL: '/products/put',
                deleteURL: '/products/delete',
                init: {},
                options: {
                    height: window.innerHeight - 430,
                    toolbar: [
                        ['edit', ['undo', 'redo']],
                        ['headline', ['style']],
                        ['style', ['bold', 'italic', 'underline', 'superscript', 'subscript', 'strikethrough', 'clear']],
                        ['fontface', ['fontname']],
                        ['fontclr', ['color']],
                        ['fontsize', ['fontsize']],
                        ['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
                        ['height', ['height']],
                        ['table', ['table']],
                        ['insert', ['link', 'picture', 'video', 'hr']],
                        ['view', ['fullscreen', 'codeview']],
                        ['help', ['help']]
                    ]
                }
            });

            GridManager.givePowerTo(vm);
            ModalManager.enableModals(vm);


            vm.selectedCat = '';
            vm.AmfTable.sortColumn = 'id';
            vm.AmfTable.sortDir = 'DESC';
            vm.AmfTable.openPage(0);

            vm.AmfTable.showAddEdit = function (item) {

                $($('.nav-active-border .nav-link')[0]).click();
                vm.init.action = item ? 'რედაქტირება' : 'დამატება';
                vm.object = {};

                if (item) {
                    vm.object = angular.copy(item);
                    vm.object.oldPrice = parseFloat(vm.object.oldPrice);
                    vm.object.newPrice = parseFloat(vm.object.newPrice);
                }

                vm.toggle();

            };

            vm.toggle = function () {
                $('#showAddEdit').toggle();
                $('#list-of-items').toggle();
            };

            vm.uploader.onCompleteItem = function (fileItem, response, status, headers) {
                if (response && response.length > 0) {
                    if (!$('#page-1').is(':visible')) {
                        vm.object.imgUrls = response[0].name
                    } else {
                        vm.AmfTable.didPressedSave = false;
                    }
                }
            };

            vm.isFileSet = function () {
                return vm.object.imgUrls !== undefined && vm.object.imgUrls !== null && vm.object.imgUrls.length > 0
            };

            vm.imageUpload = function (files, editor) {
                for (var i in files) {
                    vm.uploader.addToQueue(files[i]);
                }
                vm.uploader.uploadAll();
                vm.uploader.onCompleteItem = function (fileItem, response, status, headers) {
                    if (response && response.length > 0) {
                        if ($('#page-1').is(':visible')) {
                            for (var file in response) {
                                var fileName = response[file].name;
                                editor.insertImage(vm.editable, '/uploads/' + fileName, fileName);
                            }
                            vm.AmfTable.didPressedSave = false;
                        } else {
                            vm.object.imgUrls = response[0].name
                        }
                    }
                };
            };

            vm.uploader.onBeforeUploadItem = function (item) {
                if ($('#page-1').is(':visible')) {
                    item.formData.push({type: 'SLIDER_IMG'});
                } else {
                    item.formData.push({type: 'POST_IMG'});
                }
            };

            vm.showImage = function () {
                if (vm.object.imgUrls && vm.object.imgUrls.length > 0) {
                    return '/uploads/' + vm.object.imgUrls
                }
                return null;
            };

            vm.uploader.onCompleteAll = function () {
                if (vm.AmfTable.didPressedSave) {
                    $("input[type='file']").val('').clone(true);
                    if (vm.AmfTable.save !== undefined) {
                        vm.AmfTable.save()
                    }
                    vm.uploader.queue = [];
                }
            };

            vm.AmfTable.save = function () {
                var objectCopy = angular.copy(vm.object);

                // objectCopy.descr = $($('.redactor_editor')[0]).html();
                // objectCopy.descrEn = $($('.redactor_editor')[1]).html();
                // objectCopy.descrRu = $($('.redactor_editor')[2]).html();

                objectCopy.descr = '';
                objectCopy.descrEn = '';
                objectCopy.descrRu = '';

                $http.post(vm.saveURL, objectCopy).success(function (response) {
                    if (!response.success) {
                        vm.showErrorModal(response.errorObj);
                        return;
                    }

                    vm.showSuccessAlert("Success");
                    vm.AmfTable.reloadData();
                    $('#showAddEdit').modal('hide');

                });
            };

            vm.AmfTable.delete = function (itemId) {
                $http.post($scope.deleteURL, itemId).success(function (data) {
                    vm.AmfTable.reloadData();
                });
            };

            vm.getList = function () {
                vm.AmfTable.openPage(0);
            };

            setTimeout(function () {
                $('.form_datetime').datetimepicker({
                    language: 'ka',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 2,
                    forceParse: 0,
                    showMeridian: 1
                });
            }, 1500)

        }
    ]);
