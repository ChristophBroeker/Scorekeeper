/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */

var PlayerService = angular.module('scorekeeper.services', ['ngResource'], null);

PlayerService.factory('PlayerFactory', function($resource){
    return {
         getAllPlayerNames: function(){
             return  $resource('scripts/mocks/playerNames.json', {}, {query: {method:'GET', isArray:true}}).query();
         },

        getScoreTable: function(){
            return  $resource('scripts/mocks/scoretable.json', {}, {query: {method:'GET', isArray:true}}).query();
        },

        getAllGames: function(){
            return  $resource('scripts/mocks/games.json', {}, {query: {method:'GET', isArray:true}}).query();
        }


    };
});

