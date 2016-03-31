var app = angular.module('loginApp', ['ngRoute']);

app.config(['$routeProvider', function($routeProvider){
    console.log("Routeprovider..");
    $routeProvider
        .when('/login', {templateUrl: 'resources/html/login.html', controller:'loginCtrl'})
        .when('/home', {templateUrl: 'resources/html/home.html', controller:'homeCtrl'})
        .otherwise({redirectTo: '/login'});
}]);