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
            $scope.games = GameService.getAllGames();
            $scope.playerService = PlayerService;


            var game = {

                "teamA":
                    [

                    ],
                "teamB":[
                ],
                "teamAScore":0,
                "teamBScore":0,
                "result":"WIN_A"


            }

            $scope.newGame = game;
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
             //$scope.players = PlayerService.addNewPlayer(playerName);
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