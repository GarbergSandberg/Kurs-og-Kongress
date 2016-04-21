/**
 * Created by eiriksandberg on 05.04.2016.
 */
sessionRegisterApp.controller('OverviewCtrl', ['$scope', 'courseService', '$window', function ($scope, courseService, $window) {
    $scope.courses = {};
    $scope.panels = [];
    $scope.panels.activePanel = -1;
    $scope.$watch("panels.activePanel", function(newValue, oldValue) {
    });

    $scope.editCourse = function(id){
        self.setSessionID(id);
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

    self.setSessionID = function(id){
        courseService.setSessionStorageID(id).then(function(successCallback){
            $window.location.href = "/kursogkongress/registerCourse";
        }, function(errorCallback){
            console.log("error in setSessionID");
        });
    };

    self.loadApplication();
}]);