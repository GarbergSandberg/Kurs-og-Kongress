/**
 * Created by eiriksandberg on 21.01.2016.
 */
var myApp = angular.module('registerApp', ['ngAnimate', 'mgcrea.ngStrap', 'ngRoute']);

myApp.controller('AddSessionCtrl', ['$scope','$modal' ,'sessionService', function($scope, $modal, sessionService){
    var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerSessionModal.html', show: false});
    $scope.dates = [{id: 'Mandag'}, {id: 'Tirsdag'}, {id: 'Onsdag'}, {id: 'Torsdag'}, {id:'Fredag'}];
    $scope.date = "Empty";
    $scope.passBtnId = function(id){                            //put these in the service for cleaner code
        $scope.date = sessionService.date(id);
    }
    $scope.showModal = function() {
        myModal.$promise.then(myModal.show);
    };
    $scope.sessions = sessionService.get();
    $scope.update = function(newSession){
        sessionService.save(newSession);
    }
}]);

myApp.controller('AddCourseCtrl', ['$scope','$modal' ,'sessionService', function($scope, $modal, sessionService){
    $scope.startDate = function(date){
        $scope.startDate = date;
    }
    $scope.endDate = function(date){
        $scope.endDate = date;
    }
    $scope.roles = [];
    $scope.addRole = function(role){
        roles.push(role);
        console.log("role is pushed: " + role);
    }

}]);


