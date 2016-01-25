/**
 * Created by eiriksandberg on 21.01.2016.
 */
var myApp = angular.module('registerApp', ['ngAnimate', 'mgcrea.ngStrap', 'ngRoute']);

myApp.controller('AddSessionCtrl', function($scope, $modal){
    var myModal = $modal({scope: $scope, template: '/resources/html/registerSessionModal.html', show: false});
    $scope.showModal = function() {
        myModal.$promise.then(myModal.show);
    };
});

