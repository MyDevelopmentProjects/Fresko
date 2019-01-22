angular.module('app').controller('DefaultController',
    ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
        angular.extend($scope, {
            filter:{}
        });


    }]);
