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
    $scope.loading = true;
    $scope.$watch("panels.activePanel", function(newValue, oldValue) {});

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
                for (var i = 0; i < response.length; i++) {
                    console.log(response[i]);
                    self.getCountRegistrations(response[i], response[i].id);
                }
            $scope.loading = false;
            }, function(errorResponse){
            console.log("Error in loadApplication()");
        })};

    self.getCountRegistrations = function(course, courseID){
        publicRegistrationService.getCountRegistrations(courseID).then(function (success) {
            if (success < course.maxNumber) {
                var courseToBePushed = self.setCourse(course);
                $scope.courses.push(courseToBePushed);
                var date1 = new Date(courseToBePushed.startDate);
                var date2 = new Date(courseToBePushed.endDate);
                $scope.panels.push({
                    title: courseToBePushed.title,
                    body: courseToBePushed.description,
                    startDate: date1,
                    endDate: date2,
                    courseID: courseToBePushed.id,
                    remainingSlots: (course.maxNumber - success)
                });
            }
    }, function (error) {
            console.log("Error in getCountRegistrations(Ctrl) " + error);
        })
    };

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