/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 20.06.13
 * Time: 09:42
 * To change this template use File | Settings | File Templates.
 */



ScoreKeeper.directive("userrole", function(){
    return {
        restrict: 'A',

        scope: {
            role: '@',
            containsRole: '=',
            user: '='
        },

         //containsRole(user.roles, role)
        template: '<div class="btn" >{{role}} - {{user.name}} - {{containsRole}}</div>'


    };
});
