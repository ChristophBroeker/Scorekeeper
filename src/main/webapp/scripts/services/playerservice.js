/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */

var PlayerService = angular.module('player.services', ['ngResource'], null);

PlayerService.service('PlayerService', function($resource, $http, $filter){

       var playerList = null;

        this.selectedPlayer = {player: null};


       this.getHistoryForChart = function(){
           console.log('getHistoryForChart');
           var data ={cols: [{'id': 'date','label': 'Datum','type': 'string'},{'id': 'mean','label': 'Staerke','type': 'number'},{'id': 'deviation','label': 'Unsicherheit','type': 'number'}],
               rows: [{"c": [{"v": ""},{"v": 0},{"v": 0}]}]
           };

           angular.forEach(this.selectedPlayer.player.scoreHistory, function(item) {
                     data.rows.push({
                         "c":[
                             {
                                 "v": $filter('date')(item.captured,'dd.MM.yyyy')

                             },
                             {
                                 "v": item.mean

                             },
                             {
                                 "v": item.standardDeviation

                             }
                         ]
                     });
           });

           console.log('data: '+data.cols);
           return data;
       };
        this.getAllPlayers = function(){
            console.log("get all Players");


                playerList = $resource('rest/players', {}, {query: {method:'GET', isArray:true}}).query();

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

           for (var i = playerList.length -1; i>=0;i--){
               if(playerList[i].id == playerId){
                   return playerList[i].name;

               }
           }
           return "";

       };




        this.getPlayerIdsByName = function(playerName){

              return [12];
        };


});

