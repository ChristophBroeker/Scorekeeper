/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:43
 * To change this template use File | Settings | File Templates.
 */

var ScoreKeeperCtrl = angular.module('scorekeeper', ['scorekeeper.services', 'user.services'], null);
ScoreKeeperCtrl.config(function ($routeProvider){
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


ScoreKeeperCtrl.controller('ScoreKeeperCtrl',
    ['$scope','$route', '$location',
        function($scope, $route, $location) {



            //$scope.scoreTable = PlayerFactory.getScoreTable();

        }
    ]);
ScoreKeeperCtrl.controller('NavCtrl',
    ['$scope','$route', '$location',
        function($scope, $route, $location) {
            $scope.navClass = function (page) {

                var currentRoute = $location.path().substring(1) || 'home';
                return page === currentRoute ? 'active' : '';
            };
        }
    ]);

ScoreKeeperCtrl.controller('LoginCtrl',
    ['$scope','$route','UserService',
        function($scope, $route, UserService) {
            $scope.loginFunction = function () {
                UserService.login($scope.login, $scope.password);

            };
        }
    ]);




ScoreKeeperCtrl.controller('BoardCtrl',
    ['$scope','$route', 'PlayerService',
        function($scope, $route, PlayerService) {
         $scope.board = PlayerService.getScoreTable();


        }
    ]);

ScoreKeeperCtrl.controller('GamesCtrl',
    ['$scope','$route', 'PlayerService',
        function($scope, $route, PlayerService) {
            $scope.playerNames = PlayerService.getAllPlayerNames();
            $scope.games = PlayerService.getAllGames();

            var game = {
                "date":"2013-05-03",
                "teams":
                    [
                        {
                            "player":[{"name":"leer"}, {"name":"leer"}],
                            "points":0,
                            "result":"won"
                        },{
                        "player":[{"name":"leer"}, {"name":"leer"}],
                        "points":0,
                        "result":"lost"
                    }
                    ]

            }

            $scope.newGame = game;
            $scope.addNewGame = function(){


                PlayerService.addNewGame(game);
            };

        }
    ]);

ScoreKeeperCtrl.controller('PlayerCtrl',
    ['$scope','$route',
        function($scope, $route) {



        }
    ]);

ScoreKeeperCtrl.controller('AdminCtrl',
    ['$scope','$route',
        function($scope, $route) {



        }
    ]);