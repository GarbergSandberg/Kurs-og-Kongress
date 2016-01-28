/*
  Created by IntelliJ IDEA.
  User: Lars
  Date: 27.01.16
  Time: 09.36
  To change this template use File | Settings | File Templates.
*/
var myApp = angular.module('eventApp', ['ngAnimate', 'mgcrea.ngStrap', 'ngRoute']);

myApp.controller('AddEventCtrl', function($scope, $modal){
  var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerEventModal.html', show: false});
  $scope.showModal = function() {
    myModal.$promise.then(myModal.show);
  };
  $scope.hideModal = function() {
    myModal.$promise.then(myModal.show(false));
  };
});


myApp.controller('AddEventInfoCtrl', ['$scope', 'eventService', function($scope, eventService){
  $scope.event = eventService.get();
  $scope.update = function(newEvent){
    eventService.add(newEvent);
  }
}]);

myApp.factory('eventService', function() {
  var event = {};
  var eventService = {};

  eventService.add = function(newEvent) {
    event = angular.copy(newEvent)
  };
  eventService.get = function() {
    return event;
  };

  return eventService;
});
