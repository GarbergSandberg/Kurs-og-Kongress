sessionRegisterApp.controller('statisticsCtrl', ['$scope', 'courseService', 'statisticsService',  function ($scope, courseService, statisticsService) {
    // Kurs-stuff
    $scope.course = {};
    $scope.course.title = "heidu";


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
        var id = 0; // id må settes til kurs-id'en som blir valgt av brukeren (brukeren må ha tilgang til det kurset).
        courseService.getCourse(id).then(function(response){
            $scope.course = self.setCourse(response);
        }, function(errorResponse){
            console.log("Error in loadApplication()");
        })};

    self.loadApplication();




    //      Person/registrerings-stuff
    $scope.registrations = [];

    self.loadRegistrations = function(){
        statisticsService.getRegistrations($scope.course.courseID).then(function(response){
            self.mapRegistration(response);
        }, function(error){
            console.log("Error in getting registrations...");
        });
    };

    self.mapRegistration = function(registrations){
        for (var i = 0; i < registrations.length; i++){
            var registration = registrations[i];
            if (registration != null){
                $scope.registrations.push(registration);
            }
        }
    };

    self.loadRegistrations();


}]);
