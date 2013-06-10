/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */

var PlayerService = angular.module('scorekeeper.services', ['ngResource'], null);

PlayerService.factory('PlayerService', function($resource, $http){
    return {

         addNewPlayer: function(newPlayerName){
             console.log("add Player: "+newPlayerName);
             $http({method: 'POST',url: 'rest/players/'+newPlayerName})
                 .success(function(response) {

                     console.log('success: '+response.data);
                 })
                 .error(function(response) {
                     console.log('error: '+response);
                 });
             return  this.getAllPlayer();
         },

         getAllPlayer: function(){
             console.log("get all Players");
             return  $resource('rest/players', {}, {query: {method:'GET', isArray:true}}).query();
         },

        getScoreTable: function(){
            return  $resource('scripts/mocks/scoretable.json', {}, {query: {method:'GET', isArray:true}}).query();
        },

        getAllGames: function(){
            return  $resource('rest/games', {pageSize: 100, page:0 }, {query: {method:'GET', isArray:true}}).query();
        },

        addNewGame: function(newGame){
            console.log("add new Game");
            $http({method: 'POST',url: 'rest/games',data: newGame})
                .success(function(response) {

                    console.log('success: '+response.data);

                })
                .error(function(response) {
                    console.log('error: '+response);

                });

            return  this.getAllGames();
        }
    };
});

