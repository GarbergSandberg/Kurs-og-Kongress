/**
 * Created by eiriksandberg on 28.04.2016.
 */
/**
 * Created by eiriksandberg on 05.04.2016.
 */
sessionRegisterApp.controller('OverviewCtrlLite', ['$scope', 'courseService', '$window', function ($scope, courseService, $window) {
    $scope.user = {};
    $scope.courses = {};
    $scope.panels = [];
    $scope.panels.activePanel = -1;
    $scope.$watch("panels.activePanel", function(newValue, oldValue) {
    });
    $scope.loading = true;

    $scope.getStatistics = function(id){
        self.setSessionStatistics(id);
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
            course.startDate = new Date(c.startDate);
            //course.filterDate = $scope.fullDateConverter(course.startDate.getFullYear(), course.startDate.getMonth());
        }
        if (c.endDate != null){
            course.endDate = new Date(c.endDate);
            //course.filterDate = $scope.fullDateConverter(course.endDate.getFullYear(), course.endDate.getMonth());
        }
        if (c.id != null){
            course.id = c.id;
        }
        if (c.publicCourse != null){
            course.publicCourse = c.publicCourse;
        }
        return course;
    };

    self.loadApplication = function(){
        courseService.getNotAdminCourses().then(function (response) {
            $scope.courses = new Array();
            for (var i = 0; i < response.length; i++){
                $scope.courses.push(self.setCourse(response[i]));
                $scope.panels.push({
                    title: $scope.courses[i].title,
                    body: $scope.courses[i].description,
                    startDate: $scope.courses[i].startDate,
                    endDate: $scope.courses[i].endDate,
                    courseID: $scope.courses[i].id})
            }
            $scope.loading = false;
        }, function (error) {
            console.log("Something went wrong while getting course access");
        })};


    self.setSessionStatistics = function(id){
        courseService.setSessionStorageID(id).then(function(successCallback){
            $window.location.href = "/kursogkongress/courseStatistics";
        }, function(errorCallback){
            console.log("error in setSessionID");
        });
    };

    self.loadApplication();
}]);