angular.module('app').directive('imperioDateCud', function () {
    return {
        restrict: 'E',
        templateUrl: 'security/includes/date-cud.jsp',
        replace: true,
        scope: {
            ngModel: '='
        }
    };
});