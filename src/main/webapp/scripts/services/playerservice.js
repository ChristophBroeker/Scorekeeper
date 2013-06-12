/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */

var PlayerService = angular.module('player.services', ['ngResource'], null);

PlayerService.service('PlayerService', function($resource, $http){

       var playerList = null;

        this.getAllPlayers = function(){
            console.log("get all Players");
            if(playerList == null){

                playerList = $resource('rest/players', {}, {query: {method:'GET', isArray:true}}).query();
            }

            return playerList
        };

         this.addNewPlayer = function(newPlayerName){
            console.log("add Player: "+newPlayerName);
            $http({method: 'POST',url: 'rest/players/'+newPlayerName})
                .success(function(response) {

                   // playerList = $resource('rest/players', {}, {query: {method:'GET', isArray:true}}).query();
                    playerList.push(response);
                    console.log('success: '+response);
                })
                .error(function(response) {
                    console.log('error: '+response);
                });

        };

        this.removePlayer = function(playerId){
            console.log("remove Player: "+playerId);

            $http({method: 'POST',url: 'rest/players/delete/'+playerId})
                .success(function(response) {
                     for (var i = playerList.length -1; i>=0;i--){
                         if(playerList[i].id === playerId){
                             playerList.splice(i,1);
                             break;
                         }
                     }

                    console.log('success: '+response.data);
                })
                .error(function(response) {
                    console.log('error: '+response);
                });
        };


       this.getPlayer = function(playerId){
           console.log('getPlayer');

           for (var i = playerList.length -1; i>=0;i--){
               if(playerList[i].id == playerId){
                   //playerList[i].name = playerList[i].name +1;
                   return playerList[i].name;

               }
           }
           return "";
           //return $resource('rest/players/:playerId', {playerId:playerId}, {query: {method:'GET', isArray:true}}).query();
       };


       this.getScoreTable = function(){
            return  $resource('rest/players/scoreboard', {}, {query: {method:'GET', isArray:true}}).query();
       };

        this.getPlayerIdsByName = function(playerName){

              return [12];
        };


});

