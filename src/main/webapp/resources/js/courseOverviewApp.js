/**
 * Created by eiriksandberg on 17.02.2016.
 */
var myApp = angular.module('courseOverviewApp', ['ngAnimate', 'mgcrea.ngStrap']);

myApp.controller('OverviewCtrl', ['$scope', 'courseService', '$window', function ($scope, courseService, $window) {
    $scope.courses = {};
    $scope.panels = [];
    $scope.panels.activePanel = -1;
    $scope.$watch("panels.activePanel", function(newValue, oldValue) {
        sessionStorage.cid = newValue;
        console.log(sessionStorage.cid + " = cid");
        });

    $scope.editCourse = function(id){
        $window.location.href = "/kursogkongress/registerCourse";
    };

    self.setCourse = function(courseRecieved){
        var course = {};
        var c = courseRecieved;
        if (c.title != null){
            course.title = c.title;
        }
        if (c.description != null){
            course.description = c.description;
        }
        if (c.startDate != null){
            course.startDate = c.startDate;
        }
        if (c.endDate != null){
            course.endDate = c.endDate;
        }
        if (c.id != null){
            course.id = c.id;
        }
        return course;
    };

    self.loadApplication = function(){
        courseService.getCourses().then(function(response) {
            $scope.courses = new Array();
            for (var i = 0; i < response.length; i++){
                $scope.courses.push(self.setCourse(response[i]));
                var date = new Date($scope.courses[i].startDate);
                $scope.panels.push({
                    title: $scope.courses[i].title,
                    body: $scope.courses[i].description,
                    startDate: date.toDateString(),
                    courseID: $scope.courses[i].id})
            }
        }, function(errorResponse){
            console.log("Error in loadApplication()");
        })};

    self.loadApplication();
}]);