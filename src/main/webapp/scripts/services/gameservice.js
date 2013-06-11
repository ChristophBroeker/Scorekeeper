/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */

var GameService = angular.module('game.services', ['ngResource'], null);

GameService.service('GameService', function($resource, $http){

    var gameList = null;

        this.getAllGames = function(){
            console.log("get all Games");
            if(gameList == null){

                gameList = $resource('rest/games', {pageSize: 100, page:0 }, {query: {method:'GET', isArray:true}}).query();
            }

            return gameList



        };

    this.addNewGame = function(newGame){
        console.log("add new Game");
        $http({method: 'POST',url: 'rest/games',data: newGame})
            .success(function(response) {
                gameList.push(newGame);
                console.log('success: '+response.data);
            })
            .error(function(response) {
                console.log('error: '+response);
            });
    };












});

