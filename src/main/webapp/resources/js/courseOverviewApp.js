/**
 * Created by eiriksandberg on 17.02.2016.
 */
var myApp = angular.module('courseOverviewApp', ['ngAnimate', 'mgcrea.ngStrap']);

myApp.controller('OverviewCtrl', ['$scope', 'courseService', function ($scope, courseService) {
    $scope.courses = {};

    $scope.panels = [];

    $scope.panels.activePanel = -1;

    $scope.changeCourse = function(id){
        console.log("DENNE BLIR KJØRT");
      for (var i = 0; i < $scope.courses.length; i++){
          if($scope.courses[i].id == id){
              courseService.editCourse(id);
          }
      }
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