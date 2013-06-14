/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:43
 * To change this template use File | Settings | File Templates.
 */

var ScoreKeeper = angular.module('scorekeeper', ['player.services', 'game.services', 'user.services', 'ui.bootstrap'], null);
ScoreKeeper.config(function ($routeProvider){
    $routeProvider
        .when('/board',
        {
           templateUrl: "views/board.html",
           controller: "BoardCtrl"


        }).when('/player',
        {
            templateUrl: "views/secure/player.html"

        }).when('/games',
        {
            templateUrl: "views/secure/games.html"

        }).when('/admin',
        {
            templateUrl: "views/secure/admin/admin.html"

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
ScoreKeeper.directive('googleChart', function ($timeout) {
    return {
        restrict: 'A',
        scope: {
            chart: '=chart'
        },
        link: function ($scope, $elm, $attr) {
            // Watches, to refresh the chart when its data, title or dimensions change
            $scope.$watch('chart', function () {
                draw();
            }, true); // true is for deep object equality checking

            function draw() {
                if (!draw.triggered && ($scope.chart != undefined)) {
                    draw.triggered = true;
                    $timeout(function () {
                        draw.triggered = false;

                        var dataTable = new google.visualization.DataTable($scope.chart.data, 0.5);

                        var chartWrapperArgs = {
                            chartType: $scope.chart.type,
                            dataTable: dataTable,
                            options: $scope.chart.options,
                            containerId: $elm[0]
                        };

                        var chartWrapper = new google.visualization.ChartWrapper(chartWrapperArgs);
                        google.visualization.events.addListener(chartWrapper, 'ready', function () {
                            $scope.chart.displayed = true;
                        });
                        google.visualization.events.addListener(chartWrapper, 'error', function (err) {
                            console.log("Chart not displayed due to error: " + err.message);
                        });
                        $timeout(function () {
                            chartWrapper.draw();
                        });
                    }, 0, true);
                }
            }

        }
    };
});

ScoreKeeper.controller('ScoreKeeperCtrl',
    ['$rootScope','$route', '$location','UserService',
        function($rootScope, $route, $location, UserService) {

            $rootScope.user = UserService.getUser();


            $rootScope.hasRole = function(role) {

                if ($rootScope.user === undefined) {
                    return false;
                }
                if ($rootScope.user.roles === undefined) {
                    return false;
                }
                if ($rootScope.user.roles[role] === undefined) {
                    return false;
                }

                return $rootScope.user.roles[role];
            };

            $rootScope.isLoggedIn = function(){
                if ($rootScope.user === undefined || $rootScope.user.name === 'anonymous') {
                    return false;
                }
                return true;
            };




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
    ['$scope','$route', 'GameService',
        function($scope, $route, GameService) {
         $scope.board = GameService.getScoreTable();


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
    ['$scope','$route','$dialog', 'PlayerService',
        function($scope, $route, $dialog, PlayerService) {
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



            $scope.opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                templateUrl:  'views/secure/playerchart.html', // OR: templateUrl: 'path/to/view.html',
                controller: 'PlayerChartCtrl'
            };
         $scope.openPlayerChart = function(player){
            PlayerService.selectedPlayer.player = player;
            console.log('player: '+player);
            var d = $dialog.dialog($scope.opts);
            d.open()
         };
        }
    ]);

ScoreKeeper.controller('PlayerChartCtrl',
    ['$scope','$route','PlayerService',
        function($scope, $route, PlayerService) {

            $scope.player =  PlayerService.selectedPlayer.player;


            $scope.chart = {
                "type": "AreaChart",
                "displayed": true,
                "cssStyle": "height:400px; width:400;",
                "data": PlayerService.getHistoryForChart(),
                "options": {
                    "title": "Chart fuer "+$scope.player.name,
                    "isStacked": "false",
                    "fill": 20,
                    "displayExactValues": true,
                    "vAxis": {

                        "gridlines": {
                            "count": 10
                        }
                    },
                    "hAxis": {
                        "title": "Date"

                    }
                }
            }

        }
    ]);

ScoreKeeper.controller('AdminCtrl',
    ['$scope','$route',
        function($scope, $route) {



        }
    ]);