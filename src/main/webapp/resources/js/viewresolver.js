/**
 * Created by eiriksandberg on 12.02.2016.
 */

var app = angular.module('viewresolver', ['ngRoute']);

app.config(function($routeProvider){
    $routeProvider
        .when('/', {
        template: 'registerEventModal.html'
    });
})