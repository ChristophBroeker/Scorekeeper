/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */

angular.module('user.services', ['ngResource'], null)

    .service('UserService', function ($resource, $http) {

        var userList = null;

        this.currentLogin = null;

        this.getCurrentUser = function (callback) {
            console.log('getUser');

            var user = $resource('rest/user/currentUser', {}, {get: {method: 'GET', isArray: false}});

            this.currentLogin = user.get({},
                function success(response) {
                    console.log("got User: " + response.name);

                    if (response.changedPassword == false) {
                        callback();
                    }
                },
                function err() {
                    console.log('error occured');
                });
            return this.currentLogin;
        };

        this.getAllUser = function () {
            console.log('getAllUser');
            userList = $resource('rest/user', {}, {query: {method: 'GET', isArray: true}}).query();
            return userList;

        };


        this.addNewUser = function (newUserName, firstPW) {
            console.log("add User: " + newUserName);
            $http({method: 'POST', url: 'rest/user/' + newUserName + '/' + firstPW})
                .success(function (response) {
                    console.log('success: ' + response);
                    userList.push(response);

                })
                .error(function (response) {
                    console.log('error: ' + response);
                });

        };

        this.deleteUser = function (userId) {
            console.log("delete User: " + userId);
            $http({method: 'DELETE', url: 'rest/user/' + userId})
                .success(function (response) {
                    console.log('success: ' + response);

                    for (var i = userList.length - 1; i >= 0; i--) {
                        if (userList[i].id === userId) {
                            userList.splice(i, 1);
                            break;
                        }
                    }


                })
                .error(function (response) {
                    console.log('error: ' + response);
                });

        };

        this.updateUserRoles = function (user) {
            console.log("updateUserRoles: " + user.id);
            console.log("APPADMIN: " + user.roles.APPADMIN);
            console.log("SCOREADMIN: " + user.roles.SCOREADMIN);
            console.log("USER: " + user.roles.USER);


            $http({method: 'PUT', url: 'rest/user/roles/' + user.id, data: user.roles})
                .success(function (response) {
                    console.log('success: ' + response);
                })
                .error(function (response) {
                    console.log('error: ' + response);
                });
        }

        this.changeUsersPassword = function (callback, newPassword) {

            console.log("change Users Pw: " + this.currentLogin.id);
            $http({method: 'POST', url: 'rest/user/changeUsersPassword/' + this.currentLogin.id + '/' + newPassword})
                .success(function (response) {
                    console.log('success: ' + response);
                    callback(true);
                })
                .error(function (response) {
                    console.log('error: ' + response);
                    callback(false);
                });

        };
    });

