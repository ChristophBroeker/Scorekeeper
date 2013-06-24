/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 28.05.13
 * Time: 09:43
 * To change this template use File | Settings | File Templates.
 */

var ScoreKeeper = angular.module('scorekeeper', ['player.services', 'game.services', 'user.services', 'ui.bootstrap', '$strap.directives'], null);
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




ScoreKeeper.controller('ScoreKeeperCtrl',
    ['$rootScope','$route', '$location','UserService',
        function($rootScope, $route, $location, UserService) {

            $rootScope.user = UserService.getUser();

            $rootScope.availableRoles = ["APPADMIN", "SCOREADMIN", "USER"];

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
                "cssStyle": "height:400px; width:500;",
                "data": PlayerService.getHistoryForChart(),
                "options": {
                    "title": "Chart fuer "+$scope.player.name,
                    "isStacked": "false",
                    "fill": 20,
                    "displayExactValues": true,
                    "vAxis": {
                        "maxValue": 50,
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
    ['$scope','$route', 'UserService',
        function($scope, $route, UserService) {

            $scope.addNewUser = function(){
                   alert("add New User");
            };

            $scope.saveRoles = function(user){

            };

            $scope.savedSuccessfull = function(){
                console.log('do savedSuccessfull')
            };




            $scope.userList =  UserService.getAllUser();

        }
    ]);

ScoreKeeper.directive('saveRoles', function($http) {
    return {
        scope:{
            callback: '&savedSuccessfull'
        },
        link: function (scope,element){
            element.bind("mousedown", function(){

                $('#picOne').fadeIn(1500).delay(3500).fadeOut(1500);
                scope.callback();
            })
        }
    }

})