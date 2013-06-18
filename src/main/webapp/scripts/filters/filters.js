/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 17.06.13
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */

ScoreKeeper.filter('playerName',
    ['PlayerService',
        function(PlayerService){
            return function(playerId){
                return PlayerService.getPlayer(playerId);
            }
        }
    ]);

ScoreKeeper.filter('gameListFilter',
    ['PlayerService',
        function(PlayerService){
            return function(items, playerName){
                if(playerName == null){
                    return items;
                }
                console.log('gameListFilter',arguments);
                var filtered = [];
                angular.forEach(items, function(item) {
                    var teamAPlayer1 = PlayerService.getPlayer(item.teamA[0]);
                    var teamAPlayer2 = PlayerService.getPlayer(item.teamA[1]);
                    var teamBPlayer1 = PlayerService.getPlayer(item.teamB[0]);
                    var teamBPlayer2 = PlayerService.getPlayer(item.teamB[1]);
                    if( teamAPlayer1.toLowerCase().match("^"+playerName.toLowerCase())
                        || teamAPlayer2.toLowerCase().match("^"+playerName.toLowerCase())
                        || teamBPlayer1.toLowerCase().match("^"+playerName.toLowerCase())
                        || teamBPlayer2.toLowerCase().match("^"+playerName.toLowerCase())){
                        filtered.push(item);
                    }
                });
                return filtered;
            }
        }
    ]);