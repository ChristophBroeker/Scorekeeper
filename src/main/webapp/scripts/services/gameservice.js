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

        this.getAllGames = function(pageSize, forPage){
            console.log("get all Games_ "+pageSize + " "+forPage);


                gameList = $resource('rest/games', {pageSize: pageSize, page:forPage}, {query: {method:'GET', isArray:true}}).query();


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

    this.getScoreTable = function(){
        return  $resource('rest/scoreboard', {}, {query: {method:'GET', isArray:true}}).query();
    };
});

