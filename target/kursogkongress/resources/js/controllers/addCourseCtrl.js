/**
 * Created by eiriksandberg on 05.04.2016.
 */
sessionRegisterApp.controller('AddCourseCtrl', ['$scope', '$modal', 'sessionService', 'courseService', 'hotelService', 'eventService', function ($scope, $modal, sessionService, courseService, hotelService, eventService) {
    $scope.course = {};
    $scope.roles = [];
    $scope.$watch("course.startDate", function(newValue, oldValue) {
        if ($scope.course.startDate !== undefined && $scope.course.endDate !== undefined) {
            var dates = self.getDates($scope.course.startDate, $scope.course.endDate);
            sessionService.setDates(dates);
        }
    });
    $scope.$on('setHotels', function(event, data){
        console.log("Course er satt.. broadcast..");
        $scope.course.hotels = data;
    });

    $scope.$watch("course.endDate", function(newValue, oldValue) {
        if ($scope.course.startDate !== undefined && $scope.course.endDate !== undefined) {
            var dates = self.getDates($scope.course.startDate, $scope.course.endDate);
            sessionService.setDates(dates);
        }
    });

    $scope.addRole = function (role) {
        var exists = false;
        for (var i = 0; i < $scope.roles.length; i++) {
            if ($scope.roles[i] == role) {
                exists = true;
            }
        }
        if (!exists) {
            $scope.roles.push(role);
        }
    };

    $scope.removeRole = function (role) {
        for (var i = 0; i < $scope.roles.length; i++) {
            if ($scope.roles[i] == role) {
                $scope.roles.splice(i, 1);
            }
        }
    };

    $scope.save = function (course) {
        course.roles = $scope.roles;
        sessionService.destroyTempIDs();
        eventService.destroyTempIDs();
        course.sessions = sessionService.get();
        course.events = eventService.get();
        course.hotels = hotelService.get();
        courseService.prepareForm();
        course.form = courseService.getForm();
        //course.form = undefined; // This reassures that the sending of course will not fail. Because of the complexity of Form object, this have to be sent separately and handled.
        self.sendCourse(course);
    };

    self.sendCourse = function (course) {
            courseService.sendInfo(course).then(function (successCallback) {
                console.log("Course sent" + successCallback);
            }, function (errorCallback) {
                console.log("Error in courseService.sendInfo() " + errorCallback);
            });
    };

    self.getCourse = function(id){
        courseService.getCourse(id).then(function(response){
            if (response != null){
                $scope.course = response;
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
                $scope.roles= response.roles;
            }
            if (response.sessions != null){
                sessionService.setSessions(response.sessions);
                $scope.sessions = sessionService.get();
            }
            if (response.events != null){
                eventService.setEvents(response.events);
                $scope.events = eventService.get();
            }
            if (response.form != null){
                courseService.setRecievedForm(response.form);
            }
            console.log("Skal sette hoteller.. " + response.hotels);
            if (response.hotels != null){
                hotelService.sethotels(response.hotels);
                $scope.hotels = hotelService.get();
                console.log($scope.hotels);
            }
        }, function(errorResponse){
            console.log("Error in getCourse()");
        })
    };

    Date.prototype.addDays = function(days) {
        var dat = new Date(this.valueOf());
        dat.setDate(dat.getDate() + days);
        return dat;
    };

    self.getDates = function(startDate, stopDate) {
        var dateArray = new Array();
        var currentDate = startDate;
        while (currentDate <= stopDate) {
            dateArray.push({id: currentDate.toString(),
                weekday: self.findWeekday(currentDate.getDay()),
                year: currentDate.getFullYear(),
                month: currentDate.getMonth() + 1,
                day: currentDate.getDate()});
            currentDate = currentDate.addDays(1);
        }
        return dateArray;
    };

    self.findWeekday = function(weekday){
        switch (weekday){
            case 0: return "Søndag";
            case 1: return "Mandag";
            case 2: return "Tirsdag";
            case 3: return "Onsdag";
            case 4: return "Torsdag";
            case 5: return "Fredag";
            case 6: return "Lørdag";
            default: "";
        }
    };

    var cid = sessionStorage.cid;
    console.log("cid " + cid);
    if(cid == null || cid == -1){ // not good enough check. Review this. The dirtiest fix of them all.
        $scope.course.id = -1;
    } else{
        courseService.getSessionStorageID(cid).then(function(successCallback){
            self.getCourse(successCallback);
        }, function(errorCallback){
            console.log("Something is wrong");
        });
    }
}]);