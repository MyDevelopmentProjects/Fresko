angular.module('app').service('GridManager', function ($http, Excel, $cacheFactory, FileUploader) {

    this.clearCache = function (q) {
        var $httpDefaultCache = $cacheFactory.get('$http');
        $httpDefaultCache.remove(q);
    };

    this.givePowerTo = function ($scope, pageSize) {
        setTimeout(function () {
            var scope = angular.element($(".top-menu-items")).scope();
            if (scope) {
                scope.app.topMenuItems = [];
            }
        }, 1100);
        $scope.AmfTable = {};
        $scope.AmfTable.customFilters = [];
        if (pageSize !== undefined) {
            $scope.AmfTable.pageSize = pageSize;
        } else {
            $scope.AmfTable.pageSize = 10;
        }
        $scope.AmfTable.openPage = this.openPage;
        $scope.AmfTable.pageChanged = this.pageChanged;
        $scope.AmfTable.sortBy = this.sortBy;
        $scope.AmfTable.reloadData = this.reloadData;
        $scope.AmfTable.invalidateCache = this.invalidateCache;
        $scope.AmfTable.textSearch = this.textSearch;
        $scope.AmfTable.textSearchExecute = this.textSearchExecute;
        $scope.AmfTable.pageSetUp = this.pageSetUp;
        $scope.AmfTable.setSelected = this.setSelected;
        $scope.AmfTable.selected = 0;
        $scope.AmfTable.scope = $scope;
        $scope.AmfTable.exportToExcel = this.exportToExcel;
        $scope.selected = 0;
        $scope.meta = [];
        $scope.AmfTable.uploadAndSave = this.uploadAndSave;
        $scope.AmfTable.save = this.save;
        $scope.AmfTable.showAddEdit = this.showAddEdit;
        $scope.AmfTable.delete = this.remove;
        $scope.AmfTable.didPressedSave = false;
        this.initUploader($scope);
    };

    this.uploadAndSave = function () {
        var table = this.scope;
        table.AmfTable.didPressedSave = true;
        if (table.uploader.queue.length > 0) {
            table.uploader.uploadAll();
        }
        else {
            table.uploader.queue = [];
            if (table.AmfTable.save !== undefined) {
                table.AmfTable.save()
            }
        }
    };

    this.initUploader = function ($scope) {
        $scope.uploader = new FileUploader({
            url: '/upload/file'
        });

        $scope.uploader.filters.push({
            name: 'imageFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        });

        $scope.uploader.onBeforeUploadItem = function (item) {
            item.formData.push({type: 'USER_IMG'});
        };

        $scope.uploader.onCompleteAll = function () {
            $("input[type='file']").val('').clone(true);
            if ($scope.AmfTable.save !== undefined) {
                $scope.AmfTable.save()
            }
            $scope.uploader.queue = [];
        };

        $('#showAddEdit').on("hidden.bs.modal", function () {
            $scope.uploader.queue = [];
            // Clear File Input
            $("input[type='file']").val('').clone(true);
        });
    };

    this.exportToExcel = function (tableId) {
        var exportHref = Excel.tableToExcel(tableId, 'sheet name');
        setTimeout(function () {
            location.href = exportHref;
        }, 100);
    };

    this.setSelected = function ($index) {
        this.scope.selected = $index;
    };

    this.pageChanged = function (page) {
        var table = this.scope;
        table.AmfTable.openPage(page - 1);
    };

    this.openPage = function (page) {
        var table = this.scope;
        table.AmfTable.pageNumber = page;
        table.AmfTable.currentPage = page + 1;
        table.AmfTable.reloadData();
    };

    this.textSearch = function (typeTimeOut) {
        var table = this.scope;
        if (typeTimeOut == undefined) {
            typeTimeOut = 300;
        }
        table.AmfTable.lastTypeTime = (new Date()).getTime();
        var searchStr = angular.copy(table.AmfTable.searchString);
        setTimeout(function () {
            table.AmfTable.textSearchExecute(table, typeTimeOut, searchStr);
        }, typeTimeOut + 50);
    };

    this.textSearchExecute = function (table, typeTimeOut, searchString) {
        if (this.searchString !== searchString) return;
        var curTime = (new Date()).getTime();
        if (curTime - table.AmfTable.lastTypeTime >= typeTimeOut) {
            table.AmfTable.q = searchString;
            table.AmfTable.page = 0;
            table.AmfTable.currentPage = 1;
            table.AmfTable.reloadData();
        }
    };

    this.sortBy = function (sortBy, sender) {
        var table = this.scope;
        if (table.AmfTable.sortColumn == sortBy) {
            table.AmfTable.sortDir = table.AmfTable.sortDir == 'ASC' ? 'DESC' : 'ASC';
        } else {
            table.AmfTable.sortColumn = sortBy;
            table.AmfTable.sortDir = 'ASC';
        }

        table.AmfTable.pageNumber = 0;
        table.AmfTable.currentPage = 1;

        table.AmfTable.reloadData(true);
    };

    this.pageSetUp = function () {
        pageSetUp();
    };

    this.invalidateCache = function () {
        $cacheFactory.get('$http').removeAll();
    };

    this.reloadData = function () {

        var table = this.scope;

        var query = [];

        query.page = table.AmfTable.pageNumber >= 0 ? table.AmfTable.pageNumber : 0;

        if(table.AmfTable.sortColumn && table.AmfTable.sortDir){
            query.sort = table.AmfTable.sortColumn+","+table.AmfTable.sortDir;
        }

        query.size = table.AmfTable.pageSize;

        if (table.AmfTable.customFilters !== undefined) {
            var customFilters = table.AmfTable.customFilters;
            for (var p in customFilters)
                query[p] = customFilters[p];
        }

        query.q = table.AmfTable.q;

        $http({
            url: table.url,
            method: 'GET',
            params: query
        }).success(function (data) {
          if (data.content && data.content.length === 0) {
                table.AmfTable.page = -1;
                table.AmfTable.totalItems = 0;
                table.data = data;
            }
            else {
                table.data = data;
                table.AmfTable.totalItems = data.totalElements;
                if (table.AmfTable.doOnReload !== undefined) {
                    table.AmfTable.doOnReload();
                }
                if (table.AmfTable.refreshBulkActions !== undefined) {
                    table.AmfTable.refreshBulkActions();
                }
            }
            table.AmfTable.currentPage = table.AmfTable.pageNumber + 1;
        });
    };




    this.showAddEdit = function(item) {
        var table = this.scope;
        table.init.action = item ? 'რედაქტირება' : 'დამატება';
        table.object = {};
        if (item) {
            table.object = angular.copy(item);
        }
        $('#showAddEdit').modal('show');
    }

    this.save = function() {
        var table = this.scope;
        var objectCopy = angular.copy(table.object);
        objectCopy.createdBy = { id: 1 };
        $http.post(table.saveURL, objectCopy).success(function (response) {
            if (!response.success) {
                table.showErrorModal(response.errorObj);
                return;
            }
            table.showSuccessAlert("Success");
            table.AmfTable.reloadData();
            $('#showAddEdit').modal('hide');
        });
    };

    this.remove = function(itemId) {
        var table = this.scope;
        $http.post(table.deleteURL, itemId).success(function (data) {
            if (!data.success) {
                if (data.errorObj === "RECORD_IS_USED_IN_OTHER_TABLES") {
                    table.showErrorModal("მოცემული ჩანაწერის წაშლა შეუძლებელია რადგან ის ფიქსირდება სხვა ცხრილშიც.")
                }
                return;
            }
            table.AmfTable.reloadData();
        });
    };
});