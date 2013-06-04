/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */

var UserService = angular.module('user.services', ['ngResource'], null);

UserService.factory('UserService', function($resource){
    return {

        login: function(login, password){
            console.log("login: "+login +" password: "+password);
            return  true;
        },

        createNewUser: function(login, password){
            console.log("login: "+login +" password: "+password);
            return  true;
        },

        resetPassword: function(login, newPassword){
            console.log("login: "+login +" newPassword: "+newPassword);
            return  true;
        }

    };
});

