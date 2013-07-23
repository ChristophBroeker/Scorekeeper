/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 16.07.13
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */
require.config({
    //waitSeconds: 15,

    //By default load any module IDs from js/lib
    baseUrl: 'scripts',
    //except, if the module ID starts with "app",
    //load it from the js/app directory. paths
    //config is relative to the baseUrl, and
    //never includes a ".js" extension since
    //the paths config could be for a directory.
    paths: {
        'async': "libs/async",
        'propertyParser': "libs/propertyParser",
        'goog': "libs/goog",
        'angular': "libs/angular-1.0.7",
        'lodash': "libs/lodash-1.3.1",
        'google': "libs/google-jsapi",
        'bootstrap': "libs/bootstrap.2.3.2.min",
        'jquery': "libs/jquery-2.0.1.min",
        'angStrap': "libs/angular-strap-0.7.4",
        'angular-sanitize':"libs/angular-sanitize",
        'angular-resource': "libs/angular-resource-1.0.7",
        'ui-bootstrap': "libs/ui-bootstrap-0.3.0"



    },
    shim:{
        'angular': {
            exports: 'angular',
            deps: ['jquery']
        },
        'bootstrap':{
            deps: ['jquery']
        },
        'angular-resource': {
            deps: ['angular']
        },
        'angular-sanitize': {
            deps: ['angular']
        },
        'angStrap': {
            deps:['angular', 'bootstrap', 'jquery']
        },
        'lodash': {
            exports: "_"
        },
        'ui-bootstrap': {
            deps: ['angular']
        }
    }
});
require(["angular", "filters/i18n","controller/scorekeeper", "services/playerservice", "angular-resource", "services/gameservice", "services/userservice", "ui-bootstrap", "angStrap", "filters/filters", "directives/googleChart"], function(angular){

    angular.bootstrap(document, ['scorekeeper'], null);

});

