sessionRegisterApp.controller('statisticsCtrl', ['$scope', 'courseService', 'statisticsService',  function ($scope, courseService, statisticsService) {
    // JSP-side-stuff
    $scope.showInfo = function(registration){
        self.setSessionID(registration.person.personID);
    };

    self.setSessionID = function(id){
        attenderInfoService.setSessionStorageID(id).then(function(successCallback){
            $window.location.href = "/kursogkongress/personInfo";
        }, function(errorCallback){
            console.log("error in setSessionID");
        });
    };

    // Kurs-stuff
    $scope.course = {};
    $scope.course.title = "heidu";


    self.setCourse = function(courseRecieved){
        var course = {};
        var c = courseRecieved;
        if (c.id != null){
            course.id = c.id;
        }
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
        if (c.courseFee != null){
            course.courseFee = c.courseFee;
        }
        if (c.courseSingleDayFee != null){
            course.courseSingleDayFee = c.courseSingleDayFee;
        }
        if (c.events!= null){
            course.events = c.events;
        }
        if (c.sessions != null){
            course.sessions = c.sessions;
        }
        if (c.form != null){
            course.form = c.form;
        }
        if (c.hotels != null){
            course.hotels = c.hotels;
        }
        if (c.maxNumber != null){
            course.maxNumber = c.maxNumber;
        }
        if (c.roles != null){
            course.roles = c.roles;
        }
        if (c.dayPackage != null){
            course.dayPackage = c.dayPackage;
        }
        if (c.location != null){
            course.location = c.location;
        }
        return course;
    };

    self.loadApplication = function(){
        var id = 0; // id må settes til kurs-id'en som blir valgt av brukeren (brukeren må ha tilgang til det kurset).
        courseService.getCourse(id).then(function(response){
            $scope.course = self.setCourse(response);
            self.loadRegistrations(); // Henter påmeldinger for det aktuelle kurset.
            console.log($scope.course);
        }, function(errorResponse){
            console.log("Error in loadApplication()");
        })};

    self.loadApplication();




    //      Person/registrerings-stuff
    $scope.registrations = [];

    self.loadRegistrations = function(){
        console.log("Her kommer kursID " + $scope.course.id + " , " + $scope.course.course_id);
        statisticsService.getRegistrations($scope.course.id).then(function(response){
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




}]);
