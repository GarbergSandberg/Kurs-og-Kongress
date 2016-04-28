/**
 * Created by eiriksandberg on 07.04.2016.
 */
sessionRegisterApp.controller('attenderInfoCtrl', ['$scope', 'attenderInfoService', 'statisticsService', '$window', function ($scope, attenderInfoService, statisticsService, $window) {
    $scope.registrations = [];
    $scope.selectedParticipant = {};
    $scope.dateArray = [];
    $scope.selectedDays = [];
    $scope.course = {};
    $scope.chosenHotel = {};
    $scope.checkboxAccModel = {
        c1: false
    };

    $scope.showInfo = function (registration) {
        if (registration !== undefined) {
            self.setSessionID(registration.person.personID);
        } else {
            $window.location.href = "/kursogkongress/personInfo";
        }
    };
    $scope.showInvoice = function () {
        $window.location.href = "/kursogkongress/invoice";
    };
    $scope.showInvoiceFromList = function (registration) {
        self.setSessionIDFromList(registration.person.personID);
    };

    self.resolveInfo = function () {
        var sid = sessionStorage.selectedPerson;
        if (sid !== undefined) {
            attenderInfoService.getSessionStorageID(sid).then(function (success) {
                sid = success;
                attenderInfoService.getRegistration(sid).then(function (successCallback) {
                    $scope.selectedParticipant = successCallback;
                    $scope.selectedParticipant.attendingSessions = self.findSessions($scope.selectedParticipant);
                    $scope.selectedParticipant.attendingEvents = self.findEvents($scope.selectedParticipant);
                    $scope.selectedParticipant.attendingFullCourse = self.isAttendingFullCourse($scope.selectedParticipant);
                    $scope.selectedParticipant.totalAmount = self.calculateTotal($scope.selectedParticipant.cost);
                    $scope.selectedParticipant.dates = self.convertDates($scope.selectedParticipant.dates);
                    $scope.course = $scope.selectedParticipant.course;
                    console.log($scope.selectedParticipant);
                    if ($scope.selectedParticipant.accomondation !== null){
                        self.findHotel();
                        $scope.checkboxAccModel.c1 = true;
                    } else {
                        console.log("Noe er galt.. ")
                    }
                    console.log($scope.selectedParticipant);
                }, function (error) {
                    console.log("Error in getRegistration()");
                });
            }, function (error) {
                console.log("Error in getSessionStorageID");
            });
        }
    };

    self.convertDates = function (dates) {
        var d = [];
        for (var i = 0; i < dates.length; i++) {
            d[i] = new Date(dates[i]);
        }
        return d;
    };

    self.findPerson = function (id) {
        /*       for (var i = 0; i < $scope.registrations.length; i++){
         if ($scope.registrations[i].person.personID == id){
         return $scope.registrations[i];
         }
         }*/

    };

    self.findSessions = function (registration) {
        var sessionArray = [];
        if (registration.sessionsToAttend !== null) {
            for (var i = 0; i < registration.sessionsToAttend.length; i++) {
                var u = registration.sessionsToAttend[i];
                for (var x = 0; x < registration.course.sessions.length; x++) {
                    if (u == registration.course.sessions[x].id) {
                        sessionArray.push(registration.course.sessions[x]);
                        break;
                    }
                }
            }
        }
        return sessionArray;
    };

    self.findEvents = function (registration) {
        var eventArray = [];
        if (registration.eventsToAttend !== null) {
            for (var i = 0; i < registration.eventsToAttend.length; i++) {
                var u = registration.eventsToAttend[i];
                for (var x = 0; x < registration.course.events.length; x++) {
                    if (u == registration.course.events[x].id) {
                        eventArray.push(registration.course.events[x]);
                        break;
                    }
                }
            }
        }
        return eventArray;
    };

    self.getSessions = function (cid) {
        $scope.course = courseService.getCourse(cid);
    };

    self.mapRegistration = function (registrations) {
        for (var i = 0; i < registrations.length; i++) {
            var registration = registrations[i];
            if (registration != null) {
                $scope.registrations.push(registration);
            }
        }
    };

    self.isAttendingFullCourse = function (registration) {
        var courseLength = self.getDates(new Date(registration.course.startDate), new Date(registration.course.endDate)).length;
        var daysAttending = registration.dates.length;
        if (courseLength == daysAttending) {
            return true;
        } else {
            return false;
        }
    };

    Date.prototype.addDays = function (days) {
        var dat = new Date(this.valueOf());
        dat.setDate(dat.getDate() + days);
        return dat;
    };

    self.getDates = function (startDate, stopDate) {
        var currentDate = startDate;
        while (currentDate <= stopDate) {
            $scope.dateArray.push(currentDate);
            currentDate = currentDate.addDays(1);
        }
        return $scope.dateArray;
    };

    self.calculateTotal = function (registration) {
        var total = 0;
        if (registration !== undefined) {
            for (var i = 0; i < registration.length; i++) {
                total += registration[i].amount;
            }
        }
        return total;
    };

    self.setSessionID = function (id) {
        attenderInfoService.setSessionStorageID(id).then(function (successCallback) {
            $window.location.href = "/kursogkongress/personInfo";
            console.log("ok. ");
        }, function (errorCallback) {
            console.log("error in setSessionID");
        });
    };

    self.setSessionIDFromList = function (id) {
        attenderInfoService.setSessionStorageID(id).then(function (successCallback) {
            $window.location.href = "/kursogkongress/invoice";
        }, function (errorCallback) {
            console.log("error in setSessionIDFromList");
        });
    };

    $scope.changeRegistration = function () {
        $scope.change = !$scope.change;
    };

    $scope.cancelChange = function () {
        $window.location.href = "/kursogkongress/personInfo";
    };

    $scope.sameDate = function (d1, n2) {
        var d2 = new Date(n2);
        if ((d1.getFullYear() == d2.getFullYear()) && d1.getDate() == d2.getDate()) {
            return true;
        } else {
            false;
        }
    };

    $scope.colorSession = function (session) { // Skjekk om id finnes i selectedEvents.
        for (i = 0; i < $scope.selectedParticipant.attendingSessions.length; i++) {
            if (session == $scope.selectedParticipant.attendingSessions[i]) {
                return true;
            }
        }
        return false;
    };

    $scope.selectSession = function (session) {
        var idx = $scope.selectedParticipant.attendingSessions.indexOf(session);
        var idx2 = $scope.selectedParticipant.sessionsToAttend.indexOf(session.id);
        if (idx > -1) { // Blir unchecked.
            $scope.selectedParticipant.attendingSessions.splice(idx, 1);
            $scope.selectedParticipant.sessionsToAttend.splice(idx2, 1);
        } else {
            var notOverlaps = true;
            for (i = 0; i < $scope.selectedParticipant.attendingSessions; i++) {
                if ($scope.overlaps(session.startTime, session.endTime, $scope.selectedParticipant.attendingSessions[i].startTime, $scope.selectedParticipant.attendingSessions[i].endTime)) {
                    notOverlaps = false;
                    break;
                }
            }
            if (notOverlaps) {
                console.log("Legger til session.. ");
                $scope.selectedParticipant.attendingSessions.push(session);
                $scope.selectedParticipant.sessionsToAttend.push(session.id);
            }
        }
    };

    $scope.overlaps = function (startA, endA, startB, endB) { // Hvis overlapper, return true. else false.
        if (startA <= startB && startB <= endA) return true; // b starts in a
        if (startA <= endB && endB <= endA) return true; // b ends in a
        if (startB < startA && endA < endB) return true; // a in b
        return false;
    };

    $scope.colorEvent = function (event) { // Skjekk om id finnes i selectedEvents.
        for (i = 0; i < $scope.selectedParticipant.attendingEvents.length; i++) {
            if (event == $scope.selectedParticipant.attendingEvents[i]) {
                return true;
            }
        }
        return false;
    };

    $scope.selectEvent = function (event) {
        var idx = $scope.selectedParticipant.attendingEvents.indexOf(event);
        var idx2 = $scope.selectedParticipant.eventsToAttend.indexOf(event.id);
        if (idx > -1) { // Blir unchecked.
            $scope.selectedParticipant.attendingEvents.splice(idx, 1);
            $scope.selectedParticipant.eventsToAttend.splice(idx2, 1);
        } else {
            var notOverlaps = true;
            for (i = 0; i < $scope.selectedParticipant.attendingEvents; i++) {
                if ($scope.overlaps(session.startTime, session.endTime, $scope.selectedParticipant.attendingEvents[i].startTime, $scope.selectedParticipant.attendingEvents[i].endTime)) {
                    notOverlaps = false;
                    break;
                }
            }
            if (notOverlaps) {
                console.log("Legger til event.. ");
                $scope.selectedParticipant.attendingEvents.push(event);
                $scope.selectedParticipant.eventsToAttend.push(event.id);
            }
        }
    };

    $scope.wholeCourse = function () {
        if ($scope.selectedParticipant.attendingFullCourse == true) {
            $scope.selectedParticipant.dates = [];
            for (var i = 0; i < $scope.dateArray.length; i++) {
                $scope.selectedParticipant.dates.push($scope.dateArray[i]);
            }
        } else {
            $scope.selectedParticipant.dates = [];
            console.log("Tabellen er tom")
        }
    };

    $scope.selectDay = function (day) {
        $scope.selectedParticipant.attendingFullCourse = false;
        for (var i = 0; i < $scope.selectedParticipant.dates.length; i++) {
            if ($scope.selectedParticipant.dates[i].getDate() == day.getDate()) {
                $scope.selectedParticipant.dates.splice(i, 1);
                return;
            }
        }
        $scope.selectedParticipant.dates.push(day);
        if ($scope.selectedParticipant.dates.length == $scope.dateArray.length) {
            $scope.selectedParticipant.attendingFullCourse = true;
        }
    };

    $scope.checkDate = function (day) {
        for (var i = 0; i < $scope.selectedParticipant.dates.length; i++) {
            var d = new Date($scope.selectedParticipant.dates[i]);
            if (d.getDate() == day.getDate()) {
                return true;
            }
        }
        return false;
    };

    self.findHotel = function () {
        for (var i = 0; i < $scope.course.hotels.length; i++) {
            if ($scope.course.hotels[i].id == $scope.selectedParticipant.accomondation.hotelID) {
                $scope.chosenHotel = $scope.course.hotels[i];
            }
        }
    };

    $scope.makeAccomondation = function(){
        if ($scope.checkboxAccModel.c1 == true){
            $scope.selectedParticipant.accomondation = {};
        }
    };

    $scope.colorAccomondation = function (accomondation) { // Skjekk om id finnes i selectedEvents.
        if (accomondation == $scope.selectedAccomondation) return true;
        else {
            return false
        }
    };

    $scope.colorHotel = function (hotel) { // Skjekk om id finnes i selectedEvents.
        if ($scope.selectedParticipant.accomondation !== null){
            if (hotel.id == $scope.selectedParticipant.accomondation.hotelID) return true;
        }
        return false;
    };

    $scope.selectHotel = function (hotel) {
        $scope.selectedParticipant.accomondation.hotelID = hotel.id;
        $scope.chosenHotel = hotel;
    };

    $scope.updateRegistration = function (reg) {
        var saveAccomondation = 0; // -1 = delete,  0 = nothing.
        console.log(reg);
        if ($scope.checkboxAccModel.c1 == false){
            console.log("Sletter overnatting.");
            reg.accomondation.id = 0;
            reg.accomondation.fromDate = null;
            reg.accomondation.toDate = null;
            console.log(reg);
        }
        if ($scope.selectedParticipant.accomondation.doubleroom == false){
            $scope.selectedParticipant.accomondation.roommate = null;
        }
        attenderInfoService.updateRegistration(reg);
    };

    self.resolveInfo();
}]);