angular.module('angular-jwt.userprofile', [])
.factory("UserProfile", ["$http", "$rootScope", "urls", function ($http, $rootScope, urls) {
    var userProfile = {};
    console.log("User Profile constructed");
    var fetchUserProfile = function () {
        return $http.get(urls.BASE_API + 'security/self').then(function (response) {
            for (var prop in userProfile) {
                if (userProfile.hasOwnProperty(prop)) {
                    delete userProfile[prop];
                }
            }
            var obj = {};
            if(response.data.success){
                obj = response.data.content.userDetail
            }
            return angular.extend(userProfile, obj, {
                $refresh: function () {
                    return fetchUserProfile();
                },
                $hasRole: function (role) {
                    if (userProfile.authorities == undefined) {
                        return false;
                    }
                    for (var i in userProfile.authorities) {
                        var roleName = userProfile.authorities[i].authority;
                        if (roleName == role) {
                            return true;
                        }
                    }
                    return false;
                },
                $hasAnyRole: function (roles) {
                    if (userProfile.authorities == undefined) {
                        return false;
                    }
                    return !!userProfile.authorities.filter(function (item) {
                        return roles.indexOf(item.authority) >= 0;
                    }).length;
                },
                $isAuthenticated: function () {
                    return $rootScope.isAuthenticated;
                }
            });
        });
    };
    return fetchUserProfile();
}])
    .factory("Access", ["$q", "UserProfile", function ($q, UserProfile) {
        var Access = {
            OK: 200,
            UNAUTHORIZED: 401,
            FORBIDDEN: 403,
            SERVER_IS_DOWN: 0,
            hasRole: function (role) {
                return UserProfile.then(function (userProfile) {
                    if (userProfile.$hasRole(role)) {
                        return Access.OK;
                    } else if (userProfile.$isAuthenticated()) {
                        return $q.reject(Access.UNAUTHORIZED);
                    }
                });
            },

            hasAnyRole: function (roles) {
                return UserProfile.then(function (userProfile) {
                    if (userProfile.$hasAnyRole(roles)) {
                        return Access.OK;
                    } else if (userProfile.$isAuthenticated()) {
                        return $q.reject(Access.UNAUTHORIZED);
                    }
                });
            }
        };
        return Access;
    }])
    .directive("hasRole", function (UserProfile) {
        return {
            link: function (scope, element, attrs) {
                UserProfile.then(function (userProfile) {
                    if (userProfile.$hasRole(attrs.hasRole)) {
                        element.show();
                    } else {
                        element.hide();
                    }
                });
            }
        }
    })
    .directive("hasAnyRole", function (UserProfile) {
        return {
            link: function (scope, element, attrs) {
                UserProfile.then(function (userProfile) {
                    var roles = attrs.hasAnyRole.split(',');
                    if (userProfile.$hasAnyRole(roles)) {
                        element.show();
                    } else {
                        element.hide();
                    }
                });
            }
        }
    })