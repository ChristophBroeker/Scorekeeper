/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:43
 * To change this template use File | Settings | File Templates.
 */

var ScoreKeeper = angular.module('scorekeeper', ['player.services', 'game.services', 'user.services'], null);
ScoreKeeper.config(function ($routeProvider){
    $routeProvider
        .when('/board',
        {
           templateUrl: "views/board.html",
           controller: "BoardCtrl"


        }).when('/player',
        {
            templateUrl: "views/player.html"

        }).when('/games',
        {
            templateUrl: "views/games.html"

        }).when('/admin',
        {
            templateUrl: "views/admin.html"

        }).otherwise({
            templateUrl: "views/home.html"
        })
});

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

ScoreKeeper.controller('ScoreKeeperCtrl',
    ['$scope','$route', '$location',
        function($scope, $route, $location) {





        }
    ]);
ScoreKeeper.controller('NavCtrl',
    ['$scope','$route', '$location',
        function($scope, $route, $location) {
            $scope.navClass = function (page) {

                var currentRoute = $location.path().substring(1) || 'home';
                return page === currentRoute ? 'active' : '';
            };
        }
    ]);

ScoreKeeper.controller('LoginCtrl',
    ['$scope','$route','UserService',
        function($scope, $route, UserService) {
            $scope.loginFunction = function () {
                UserService.login($scope.login, $scope.password);

            };
        }
    ]);




ScoreKeeper.controller('BoardCtrl',
    ['$scope','$route', 'PlayerService',
        function($scope, $route, PlayerService) {
         $scope.board = PlayerService.getScoreTable();


        }
    ]);

ScoreKeeper.controller('GamesCtrl',
    ['$scope','$route', 'PlayerService', 'GameService',
        function($scope, $route, PlayerService, GameService) {
            $scope.playerNames = PlayerService.getAllPlayers();

            $scope.itemsPerPage = 10;
            $scope.currentListPage = 0;
            $scope.games = GameService.getAllGames($scope.itemsPerPage, $scope.currentListPage);
            var game = {"teamA":[],
                "teamB":[],
                "teamAScore":0,
                "teamBScore":0,
                "result":"WIN_A"
            }

            $scope.newGame = game;
            $scope.showMoreItems = function(){
                $scope.currentListPage = 0;
                $scope.games =GameService.getAllGames($scope.itemsPerPage,$scope.currentListPage);
            };
            $scope.raiseListPage = function(){
                 if($scope.games.length == $scope.itemsPerPage){
                     $scope.currentListPage = $scope.currentListPage + 1;
                     $scope.games =GameService.getAllGames($scope.itemsPerPage,$scope.currentListPage);
                 }

            };
            $scope.derateListPage = function(){
                if($scope.currentListPage > 0){
                    $scope.currentListPage = $scope.currentListPage - 1;
                    $scope.games =GameService.getAllGames($scope.itemsPerPage,$scope.currentListPage);
                }

            };
            $scope.addNewGame = function(){
                if(game.teamAScore > game.teamBScore){
                    game.result = "WIN_A";
                }else if(game.teamAScore < game.teamBScore){
                    game.result = "WIN_B";
                }else{
                    game.result = "DRAW";
                }
                GameService.addNewGame(game);

            };

        }
    ]);

ScoreKeeper.controller('PlayerCtrl',
    ['$scope','$route', 'PlayerService',
        function($scope, $route, PlayerService) {
         $scope.players = PlayerService.getAllPlayers();

         $scope.addNewPlayer = function(){
             console.log('CTRL - addNewPlayer');
             var playerName = $scope.newPlayer;

             PlayerService.addNewPlayer(playerName);
         };

         $scope.removePlayer = function(playerId){
             console.log("removePlayer: "+playerId);
             PlayerService.removePlayer(playerId);
          };
        }
    ]);

ScoreKeeper.controller('AdminCtrl',
    ['$scope','$route',
        function($scope, $route) {



        }
    ]);