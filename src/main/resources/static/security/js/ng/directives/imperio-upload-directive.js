angular.module('app').directive('imperioUpload', function ($rootScope, upload) {
    return {
        restrict: 'A',
        scope: true,
        link: function (scope, element, attr) {
            element.bind('change', function () {
                var formData = new FormData();
                for (var file in element[0].files) {
                    formData.append('file', element[0].files[file]);
                }
                var type = $(element).data('type');
                var org = $(element).data('org');
                formData.append('type', type);
                if (org) formData.append('organisation', org);
                else formData.append('organisation', "");
                upload('upload/file', formData, function (callback) {
                    $rootScope.$broadcast('upload-finished', {data: callback});
                });
            });
        }
    };
});
