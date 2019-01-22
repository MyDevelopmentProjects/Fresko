angular.module('app').controller('SignInController',
    ['$scope', '$http', '$localStorage', '$location', 'urls', 'GridManager', 'ModalManager', 'authManager',
        function ($scope, $http, $localStorage, $location, urls, GridManager, ModalManager,  authManager) {

        ModalManager.enableModals($scope);
        $scope.auth = function () {
            $(".loader").show();
            $http.post(urls.BASE_API + 'security/loginWithCfg', {
                type:"SIMPLE",
                credentials:{
                    username: $scope.auth.username,
                    password: $scope.auth.password
                }
            }).success(function (response) {
                delete $localStorage.token;
                if(response.success){
                    $localStorage.token = response.content.token;
                    authManager.doAauthentication();
                }
                $(".loader").hide();
            })
            .error(function (response) {
                delete $localStorage.token;
                if(!response.success){
                    switch (response.errorObj){
                        case "INVALID_USERNAME_OR_PASSWORD":{
                            $.notify("მსგავსი მომხმარებელი არ მოიძებნა", "error");
                            break;
                        }
                        case "VALIDATION_ERROR":{
                            $.notify("ვალიდაცია ვერ მოხერხდა", "error");
                            break;
                        }
                    }
                }
                $(".loader").hide();
            });
        };

}]);
