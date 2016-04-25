/**
 * Created by eiriksandberg on 25.04.2016.
 */
/**
 * Created by eiriksandberg on 05.04.2016.
 */
publicRegistrationApp.controller('PublicRegistrationCtrl', ['$scope', 'publicRegistrationService', '$window', function ($scope, publicRegistrationService, $window) {
    $scope.courses = {};
    $scope.panels = [];
    $scope.panels.activePanel = -1;
    $scope.$watch("panels.activePanel", function(newValue, oldValue) {
    });

    $scope.registration = function(id, type){
        self.setSessionID(id, type);
    };

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
        publicRegistrationService.getPublicCourses().then(function(response) {
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

    self.setSessionID = function(id, type){
        publicRegistrationService.setSessionStorageID(id).then(function(successCallback){
            if(type == 'single'){
                $window.location.href = "/kursogkongress/singleRegistration";
            }
            if(type == 'group'){
                $window.location.href = "/kursogkongress/groupRegistration";
            }
        }, function(errorCallback){
            console.log("error in setSessionID");
        });
    };

    self.loadApplication();
}]);