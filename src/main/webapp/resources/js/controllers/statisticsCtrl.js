sessionRegisterApp.controller('statisticsCtrl', ['$scope', 'courseService', 'statisticsService', 'sessionService', 'eventService', 'hotelService', 'attenderInfoService', '$window',  function ($scope, courseService, statisticsService, sessionService, eventService, hotelService, attenderInfoService, $window) {
    $scope.course = {};
    $scope.countReg = {};
    $scope.registrations = [];
    $scope.registrationsID = [];
    $scope.numberOfcourseFee = {};
    $scope.numberOfcourseSingleDayFee = {};
    $scope.numberOfDaypackages = {};
    $scope.numberOfEvents = [];
    $scope.showInfo = function(id){
        self.setSessionID(id);
    };
    $scope.loading = true;

    $scope.getCountRegistrations = function(){ // Brukes ikke? men fungerer..!
        statisticsService.getCountRegistrations($scope.course.id).then(function(result) {
            $scope.countReg = result;
        });
    };

    $scope.sendEmail = function(){
        statisticsService.getEmails($scope.course.id).then(function (emails) {
            window.location.href = self.generateEmailString(emails);
        })
    };

    $scope.getTotal = function(numb){
        var a = ($scope.numberOfcourseFee.total + $scope.numberOfcourseSingleDayFee.total + $scope.numberOfDaypackages.total);
        var b = 0;
        for (var i = 0; i<$scope.numberOfEvents.length; i++){
            b += $scope.numberOfEvents[i].total;
        }
        if (numb == 0){ // CourseTotal.
            return a;
        } else if (numb == 1){ // EventTotal
            return b;
        } else return a+b; // Total.
    };

    self.setSessionID = function(id){
        attenderInfoService.setSessionStorageID(id).then(function(successCallback){
            $window.location.href = "/kursogkongress/personInfo";
        }, function(errorCallback){
            console.log("error in setSessionID");
        });
    };

    $scope.courseEconomics = function(){
        $window.location.href = "/kursogkongress/courseEconomics";
    };

    self.getCourse = function(id){
        courseService.getCourse(id).then(function(response){
            if (response != null){
                $scope.course = response;// NB! Course contains form. Must be declared undefined before sending back to server.
            }
            if (response.startDate != null){
                $scope.course.startDate = new Date(response.startDate);
            }
            if (response.endDate != null){
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
            if (response.hotels != null){
                hotelService.sethotels(response.hotels);
                $scope.course.hotels = hotelService.get();
            }
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

    self.loadRegistrations = function(id){
        statisticsService.getRegistrations(id).then(function(response){
            self.mapRegistration(response);
            if($scope.countReg !== 0){
                self.getNumberOfPayments();
            }
            self.getNumberOfEvents();
            $scope.loading = false;
        }, function(error){
            console.log("Error in getting registrations...");
        });
    };

    self.mapRegistration = function(registrations){
        for(var i = 0; i < registrations.length; i++){
            $scope.registrations.push(registrations[i]);
            $scope.registrationsID.push(registrations[i].registrationID);
        }
        $scope.countReg = registrations.length;
    };

    var cid = sessionStorage.cid;
    console.log("cid " + cid);
    if(cid == null || cid == -1){
        $scope.course.id = -1; // Should return error page. Skal være -1.
    } else{
        courseService.getSessionStorageID(cid).then(function(success){
            self.getCourse(success);
            self.loadRegistrations(success); // Henter påmeldinger for det aktuelle kurset.
        }, function(error){
            console.log("ERROR I GETCOURSE..");
        });
    }

    ///////////////////////////  Course-"economics" ///////////

    self.getNumberOfPayments = function(){ // Finds number of daypackages, sends with ID form course.daypackage
        statisticsService.getNumberOfPayments($scope.registrationsID, "Dagpakke").then(function(result) { // Arraylist med alle ID til registreringer til kurset + description.
            $scope.numberOfDaypackages.number = result;
            $scope.numberOfDaypackages.total = ($scope.numberOfDaypackages.number * $scope.course.dayPackage);
        });
        statisticsService.getNumberOfPayments($scope.registrationsID, "Kursavgift").then(function(result) {
            $scope.numberOfcourseFee.number = result;
            $scope.numberOfcourseFee.total = ($scope.numberOfcourseFee.number * $scope.course.courseFee);
        });
        statisticsService.getNumberOfPayments($scope.registrationsID, "Kursavgift Dag").then(function(result) {
            $scope.numberOfcourseSingleDayFee.number = result;
            $scope.numberOfcourseSingleDayFee.total = ($scope.numberOfcourseSingleDayFee.number * $scope.course.courseSingleDayFee);
        });
    };

    self.getNumberOfEvents = function(){
        for (var i = 0; i<$scope.course.events.length; i++){
            self.sendGetNumberOfEvents($scope.course.events[i]);
        }
        $scope.loading = false;
    };

    self.sendGetNumberOfEvents = function(event){
        var temp = {};
        temp.title = event.title;
        temp.id = event.id;
        temp.price = event.price;
        statisticsService.getNumberOfEvents(event.id).then(function(result) {
            if (result == null){
                temp.number = "-";
                temp.total = "-";
            } else{
                temp.number = result;
                temp.total = (event.price * temp.number);
                $scope.numberOfEvents.push(temp);
            }
        });
    };

    self.generateEmailString = function(emails){
        var string = "mailto:?bcc=";
        for (var i = 0; i < emails.length; i++){
            if (i == (emails.length - 1)){
                string += emails[i];
            } else{
                string += emails[i] + ",";
            }
        }
        return string;
    }
}]);
