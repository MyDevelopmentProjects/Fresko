(function () {
    'use strict';
    angular
        .module('app')
        .controller('BaseController', BaseController);

    BaseController.$inject = ['$scope', '$http', '$localStorage', '$location', 'urls', 'GridManager', 'ModalManager', 'authManager'];
    function BaseController($scope, $http, $localStorage, $location, urls, GridManager, ModalManager, authManager) {

        var vm = $scope;

        angular.extend(vm, {
            url: '',
            saveURL: '',
            deleteURL: '',
            init: {}
        });

        GridManager.givePowerTo(vm);
        ModalManager.enableModals(vm);

        vm.showAddEdit = showAddEdit;
        vm.save = save;
        vm.delete = remove;

        function showAddEdit(item) {
            vm.init.action = item ? 'რედაქტირება' : 'დამატება';
            vm.object = {};
            if (item) {
                vm.object = angular.copy(item);
            }
            $('#showAddEdit').modal('show');
        }

        function save() {
            var objectCopy = angular.copy(vm.object);
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

        function remove(itemId) {
            $http.post(vm.deleteURL, itemId).success(function (data) {
                if (!data.success) {
                    if (data.errorObj === "RECORD_IS_USED_IN_OTHER_TABLES") {
                        vm.showErrorModal("მოცემული ჩანაწერის წაშლა შეუძლებელია რადგან ის ფიქსირდება სხვა ცხრილშიც.")
                    }
                    return;
                }
                vm.AmfTable.reloadData();
            });
        };
    }
})();