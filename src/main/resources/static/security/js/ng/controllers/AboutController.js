angular.module('app').controller('AboutController',
    ['$rootScope', '$scope', '$http', 'GridManager', 'ModalManager', 'FileUploader', '$sce',
        function ($rootScope, $scope, $http, GridManager, ModalManager, FileUploader, $sce) {

            var vm = $scope;

            var sectionsToSave = [];
            var sectionHasBeenChanged = false;

            angular.extend(vm, {
                url: '/about/list',
                saveURL: '/about/put',
                byId: '/about/byId',
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
            $scope.AmfTable.openPage(0)

            vm.AmfTable.showAddEdit = function (item) {

                $('.redactor_editor').html('');
                $('#edit, #editEn, #editRu').html('')

                $($('.nav-active-border .nav-link')[0]).click();
                vm.init.action = 'რედაქტირება';
                vm.object = {};

                if (item) {

                    $http.get($scope.byId + "?id=" + item.id).success(function (resp) {
                        $scope.object = resp
                        $($('.redactor_editor')[0]).html(vm.object.descr);
                        $($('.redactor_editor')[1]).html(vm.object.descrRu);
                        $($('.redactor_editor')[2]).html(vm.object.descrEu);
                        $('#edit').html(vm.object.descr);
                        $('#editEn').html(vm.object.descrEu);
                        $('#editRu').html(vm.object.descrRu);
                    })

                }

                vm.toggle();

                setTimeout(function () {
                    $('#edit, #editEn, #editRu').redactor({
                        lang: 'ge',
                        iframe: false,
                        linkProtocol: '//',
                        imageUpload: '/upload/single',
                        fileUpload: '/upload/single',
                        buttons: ['html', 'formatting',  'bold', 'italic', 'deleted', 'underline', 'unorderedlist', 'orderedlist',
                            'outdent', 'indent', 'image', 'video', 'table', 'link', 'alignment'],
                        plugins: ['fileupload', 'fontcolor', 'fontsize', 'fullscreen'],
                        toolbarFixed: true,
                        minHeight: 400,
                        deniedTags: ['font'],
                        placeHolder: true,
                        removeEmptyTags: false,
                        tidyHtml: false,
                        convertDivs: false,
                        xhtml: false
                    });
                }, 200)
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

            vm.getUrl = function () {
                if (vm.object && vm.object.videoUrls) {
                    return $sce.trustAsResourceUrl("https://www.youtube.com/embed/" + vm.object.videoUrls + "?rel=0&amp;controls=0");
                }
                return "";
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
                objectCopy.createdBy = {id: 1};
                objectCopy.category = {id: 1};
                objectCopy.orderNum = 0;
                objectCopy.active = true;

                if (!objectCopy.numberOfViews) {
                    objectCopy.numberOfViews = 0;
                }

                objectCopy.descr = $($('.redactor_editor')[0]).html();
                objectCopy.descrEu = $($('.redactor_editor')[1]).html();
                objectCopy.descrRu = $($('.redactor_editor')[2]).html();

                $http.post(vm.saveURL, objectCopy).success(function (response) {
                    if (!response.success) {
                        vm.showErrorModal(response.errorObj);
                        return;
                    }

                    if (sectionHasBeenChanged) {
                        if (response.content.id) {
                            vm.savePS(response.content.id);
                        } else {
                            vm.savePS(vm.object.id);
                        }
                    }

                    vm.showSuccessAlert("Success");
                    vm.AmfTable.reloadData();
                    $('#showAddEdit').modal('hide');

                });
            };

            vm.savePS = function (id) {
                $http.post(vm.savePSURL + '?postId=' + id, sectionsToSave, function () {

                })
            };

            vm.AmfTable.delete = function (itemId) {
                $http.post("/post/removePost", itemId).success(function () {
                    vm.AmfTable.reloadData();
                });
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
