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
    ['$scope','UserService', '$dialog',
        function($scope, UserService, $dialog) {

             console.log("ScoreKeeperCtrl");

            $scope.firstLogin = function(){
                var opts = {
                    backdrop: true,
                    keyboard: true,
                    backdropClick: false,
                    templateUrl:  'views/secure/newPassword.html',
                    controller: 'NewPasswordCtrl'

                };

                 $scope.pwDialog = $dialog.dialog(opts);
                 $scope.pwDialog.open();

            }

            UserService.getCurrentUser($scope.firstLogin);
            $scope.username = UserService.currentLogin.name;


            $scope.availableRoles = ["APPADMIN", "SCOREADMIN", "USER"];

            $scope.hasRole = function(role) {

                if (UserService.currentLogin === undefined) {
                    return false;
                }
                if (UserService.currentLogin.roles === undefined) {
                    return false;
                }
                if (UserService.currentLogin.roles[role] === undefined) {
                    return false;
                }

                return UserService.currentLogin.roles[role];
            };

            $scope.isLoggedIn = function(){

                if ( UserService.currentLogin === undefined || UserService.currentLogin.name === 'anonymous') {
                    return false;
                }
                $scope.username = UserService.currentLogin.name;
                return true;
            };




        }
    ]);

ScoreKeeper.controller('NewPasswordCtrl',
    ['$scope', 'UserService', 'dialog',
        function($scope, UserService, dialog) {
             $scope.changePWSuccessful = false;
             $scope.changePassword = function(){

                 if($scope.pw1 != undefined && $scope.pw1.trim().length > 0)    {
                     if($scope.pw1 == $scope.pw2){
                         UserService.changeUsersPassword($scope.changeCallback,$scope.pw1);
                     }else{
                         $scope.message = "Die Passwoerter stimmen nicht ueberein!";
                     }
                 }else{
                     $scope.message = "Bitte geben Sie ein gueltiges Passwort an!";
                 }


             };


            $scope.changeCallback = function(successfull){
                         console.log("successfully changed pw: "+successfull);
                $scope.changePWSuccessful = successfull;
                $scope.message = "Das Passwort wurde erfolgreich gespeichert";
            };

            $scope.closePopUp = function(){
                dialog.close();
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

            $scope.newUserPassword = "changeMe";
            $scope.addNewUser = function(){
                UserService.addNewUser($scope.newUser, $scope.newUserPassword);
            };
            $scope.deleteUser = function(userId){
                UserService.deleteUser(userId);
            };

            $scope.saveRoles = function(user){
                  UserService.updateUserRoles(user);

            };

            $scope.savedSuccessfull = function(){
                console.log('do savedSuccessfull')
            };




            $scope.userList =  UserService.getAllUser();

        }
    ]);

