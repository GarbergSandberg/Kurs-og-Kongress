/**
 * Created by eiriksandberg on 21.01.2016.
 */
var myApp = angular.module('registerApp', ['ngAnimate', 'mgcrea.ngStrap', 'ngRoute']);

myApp.controller('AddSessionCtrl', function($scope, $modal){
    var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerSessionModal.html', show: false});
    $scope.showModal = function() {
        myModal.$promise.then(myModal.show);
    };
    $scope.hideModal = function() {
        myModal.$promise.then(myModal.show(false));
    };
});


myApp.controller('AddInfoCtrl', ['$scope', 'sessionService', function($scope, sessionService){
    $scope.session = sessionService.get();
    $scope.update = function(newSession){
        sessionService.add(newSession);
    }
}]);

myApp.factory('sessionService', function() {
    var session = {};
    var sessionService = {};

    sessionService.add = function(newSession) {
        session = angular.copy(newSession)
    };
    sessionService.get = function() {
        return session;
    };

    return sessionService;
});
