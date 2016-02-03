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

myApp.controller('AddCourseCtrl', ['$scope','$modal' ,'sessionService', 'courseService', function($scope, $modal, sessionService, courseService){
    $scope.sessions = sessionService.get();
    $scope.startDate = function(date){
        $scope.startDate = date;
    }
    $scope.endDate = function(date){
        $scope.endDate = date;
    }
    $scope.roles = [];
    $scope.addRole = function(role){
        var exists = false;
        for (var i = 0; i < $scope.roles.length; i++){
            if($scope.roles[i] == role){
                exists = true;
            }
        }
        if(!exists){
            $scope.roles.push(role);
        }
    };
    $scope.removeRole = function(role){
        for (var i = 0; i < $scope.roles.length; i++){
            if ($scope.roles[i] == role){
                $scope.roles.splice(i,1);
            }
        }
    };
    $scope.addedRoleAlert = {
        "content": "Ny rolle lagt til",
        "type": "info"
    };
    $scope.save = function(course){
        console.log(course.title);
        console.log(course.description);
        console.log(course.startDate);      //remove this
        console.log(course.endDate);
        console.log(course.maxNumber);
        console.log(course.location);
        course.roles = $scope.roles;
        course.sessions = $scope.sessions;
        self.send(course);
    };

    self.send = function(course){
        courseService.sendInfo(course).then(function(success){
            console.log("Course sent" + course);
        }, function(error){
            console.log("Error!!!");
        })
    }
}]);


