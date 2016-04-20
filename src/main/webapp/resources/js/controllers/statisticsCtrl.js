sessionRegisterApp.controller('statisticsCtrl', ['$scope', 'courseService', 'statisticsService', 'sessionService', 'eventService', 'hotelService', 'attenderInfoService', '$window',  function ($scope, courseService, statisticsService, sessionService, eventService, hotelService, attenderInfoService, $window) {
    // JSP-side-stuff
    $scope.showInfo = function(registration){
        self.setSessionID(registration);

    };

    self.setSessionID = function(registration){
        attenderInfoService.setSessionStorageID(registration).then(function(successCallback){
            $window.location.href = "/kursogkongress/personInfo";
        }, function(errorCallback){
            console.log("error in setSessionID");
        });
    };



    // Kurs-stuff
    $scope.course = {};
    $scope.countReg = {};

    $scope.getCountRegistrations = function(){ // Brukes ikke? men fungerer..!
        var a = {};
        console.log("Trykker på knappen");
        statisticsService.getCountRegistrations($scope.course.id).then(function(result) {
            console.log(result);
            $scope.countReg = result;
        });
    };

    self.getCourse = function(id){
        courseService.getCourse(id).then(function(response){
            if (response != null){
                $scope.course = response;// NB! Course contains form. Must be declared undefined before sending back to server.
            }
            if (response.startDate != null){
                console.log("startDate.." + response.startDate);
                $scope.course.startDate = new Date(response.startDate);
            }
            if (response.endDate != null){
                console.log("endDate.." + response.endDate);
                $scope.course.endDate = new Date(response.endDate);
            }
            if (response.title != null){
                $scope.course.title = response.title;
            }
            if (response.description != null){
                $scope.course.description = response.description;
            }
            if (response.maxNumber != null){
                $scope.course.maxNumber = response.maxNumber;
            }
            if (response.location != null){
                $scope.course.location = response.location;
            }
            if (response.courseFee != null){
                $scope.course.courseFee = response.courseFee;
            }
            if (response.courseSingleDayFee != null){
                $scope.course.courseSingleDayFee = response.courseSingleDayFee;
            }
            if (response.dayPackage != null){
                $scope.course.dayPackage = response.dayPackage;
            }
            if (response.roles != null){
                $scope.course.roles= response.roles;
            }
            if (response.sessions != null){
                sessionService.setSessions(response.sessions);
                $scope.course.sessions = sessionService.get();
            }
            if (response.events != null){
                eventService.setEvents(response.events);
                $scope.course.events = eventService.get();
            }
            if (response.form != null){
                courseService.setRecievedForm(response.form);
            }
            console.log("Skal sette hoteller.. " + response.hotels);
            if (response.hotels != null){
                hotelService.sethotels(response.hotels);
                $scope.course.hotels = hotelService.get();
            }
            console.log($scope.course);
        }, function(errorResponse){
            console.log("Error in getCourse()");
        })
    };

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
        var id = 1; // id må settes til kurs-id'en som blir valgt av brukeren (brukeren må ha tilgang til det kurset).
        courseService.getCourse(id).then(function(response){
            $scope.course = self.setCourse(response);
            console.log($scope.course);
        }, function(errorResponse){
            console.log("Error in loadApplication()");
        })};

   // self.loadApplication();




    //      Person/registrerings-stuff
    $scope.registrations = [];

    self.loadRegistrations = function(id){
        console.log("Her kommer kursID " + id);
        statisticsService.getRegistrations(id).then(function(response){
            console.log(response);
            self.mapRegistration(response);
        }, function(error){
            console.log("Error in getting registrations...");
        });
    };

    self.mapRegistration = function(registrations){
        $scope.countReg = registrations.length;
        console.log("mapRegistration lengde på registrasjon: " + $scope.countReg);
        for (var i = 0; i < registrations.length; i++){
            var registration = registrations[i];
            console.log(registration);
            if (registration !== null){
                console.log("Registrasjon pushes. ");
                $scope.registrations.push(registration);
            }
        }
        console.log($scope.registrations);
    };

    var cid = sessionStorage.cid;
    console.log("cid " + cid);
    if(cid == null || cid == -1){ // not good enough check. Review this. The dirtiest fix of them all.
        $scope.course.id = -1;
    } else{
        console.log("Henter course med id " + cid);
        self.getCourse(cid);
        self.loadRegistrations(cid); // Henter påmeldinger for det aktuelle kurset.
    }
}]);
