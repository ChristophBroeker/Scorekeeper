/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */

var UserService = angular.module('user.services', ['ngResource'], null);

UserService.service('UserService', function($resource, $http){

    this.getUser = function() {
        console.log('getUser');


        return $resource('rest/user', {}, {query: {method:'GET', isArray:false}}).query();
    };

    this.getAllUser = function() {
        console.log('getAllUser');

        return $resource('scripts/mocks/user.json', {}, {query: {method:'GET', isArray:true}}).query();

    };


    this.addNewUser = function(userName, roles) {
        console.log('addNewUser');
    };
});

