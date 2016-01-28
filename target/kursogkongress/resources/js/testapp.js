/**
 * Created by eiriksandberg on 21.01.2016.
 */
var myApp = angular.module('myApp', ['ngAnimate', 'mgcrea.ngStrap', 'ngRoute']);

myApp.controller('GreetingController', function($scope){
    $scope.modal = {
        "title": "Title",
        "content": "Hello Modal<br />This is a multiline message!"
    };
});