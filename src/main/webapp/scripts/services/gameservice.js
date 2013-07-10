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

    var currentPlan = {plan:[]};
    this.getCurrentGamePlan = function(){
        console.log("getCurrentGamePlan");
        currentPlan.plan = $resource('rest/games/suggested', {}, {query: {method:'GET', isArray:true}}).query();
        return currentPlan;
    }

    this.createNewGamePlan = function(){
         console.log('create new Game plan');
        $http({method: 'POST',url: 'rest/games/suggested'})
            .success(function(response) {

                currentPlan.plan = $resource('rest/games/suggested', {}, {query: {method:'GET', isArray:true}}).query();
                console.log('success: '+response.data);
            })
            .error(function(response) {
                console.log('error: '+response);
            });
        //this.currentPlan = $resource('rest/games/suggested', {}, {query: {method:'POST', isArray:true}}).query();
    }
});

