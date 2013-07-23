/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 17.06.13
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */


angular.module('scorekeeperFilters', [])
    .filter('playerName',
        ['PlayerService',
            function (PlayerService) {
                return function (playerId) {
                    return PlayerService.getPlayer(playerId);
                }
            }
        ])

    .filter('gameListFilter',
        ['PlayerService',
            function (PlayerService) {
                return function (items, playerName) {
                    if (playerName == null) {
                        return items;
                    }
                    console.log('gameListFilter', arguments);
                    var filtered = [];
                    angular.forEach(items, function (item) {
                        var teamAPlayer1 = PlayerService.getPlayer(item.teamA[0]);
                        var teamAPlayer2 = PlayerService.getPlayer(item.teamA[1]);
                        var teamBPlayer1 = PlayerService.getPlayer(item.teamB[0]);
                        var teamBPlayer2 = PlayerService.getPlayer(item.teamB[1]);
                        if (teamAPlayer1.toLowerCase().match("^" + playerName.toLowerCase())
                            || teamAPlayer2.toLowerCase().match("^" + playerName.toLowerCase())
                            || teamBPlayer1.toLowerCase().match("^" + playerName.toLowerCase())
                            || teamBPlayer2.toLowerCase().match("^" + playerName.toLowerCase())) {
                            filtered.push(item);
                        }
                    });
                    return filtered;
                }
            }
        ])

    .filter('highlight', function () {
        return function (text, searches) {
            var result = text;
            for (var i = 0; i < searches.length; i++) {
                var search = searches[i];
                if (search || angular.isNumber(search)) {
                    text = text.toString();
                    search = search.toString();
                    var cssClass = "ui-match" + (i + 1);
                    if (text.toUpperCase().indexOf(search.toUpperCase()) > -1) {
                        result = text.replace(new RegExp(search, 'gi'), '<span class="' + cssClass + '">$&</span>');
                    }


                }
            }
            return result;
        };
    });