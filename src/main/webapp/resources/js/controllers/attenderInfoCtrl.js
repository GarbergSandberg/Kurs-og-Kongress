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

    $scope.deleteRegistration = function(registration){
        attenderInfoService.deleteRegistration(registration).then(function (response) {
            if(response == true){
                alert("Påmelding er slettet");
                $window.location.href = "/kursogkongress/personInfo";
            } else{
                alert("Det skjedde noe galt. Prøv igjen.");
                $window.location.href = "/kursogkongress/courseStatistics";
            }
        });
    }

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
                    $scope.selectedParticipant.totalAmount = self.calculateTotal($scope.selectedParticipant.cost, $scope.selectedParticipant.attendingEvents);
                    $scope.selectedParticipant.dates = self.convertDates($scope.selectedParticipant.dates);
                    $scope.course = $scope.selectedParticipant.course;
                    console.log($scope.selectedParticipant);
                    self.setForm();
                    if ($scope.selectedParticipant.accomondation !== null) {
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

    self.setForm = function () {
        for (var i = 0; i<$scope.selectedParticipant.optionalPersonalia.length; i++) {
            if ($scope.selectedParticipant.optionalPersonalia[i] != undefined) {
                if ($scope.selectedParticipant.optionalPersonalia[i].parameter == "true") {
                    $scope.selectedParticipant.optionalPersonalia[i].parameter = true;
                    console.log("endret.. ");
                } else if ($scope.selectedParticipant.optionalPersonalia[i].parameter == "false") {
                    $scope.selectedParticipant.optionalPersonalia[i].parameter = false;
                    console.log("endret.. ");
                }
            }
        }
        for (var i = 0; i<$scope.selectedParticipant.optionalWorkplace.length; i++) {
            if ($scope.selectedParticipant.optionalWorkplace[i] != undefined) {
                if ($scope.selectedParticipant.optionalWorkplace[i].parameter == "true") {
                    $scope.selectedParticipant.optionalWorkplace[i].parameter = true;
                } else if ($scope.selectedParticipant.optionalWorkplace[i].parameter == "false") {
                    $scope.selectedParticipant.optionalWorkplace[i].parameter = false;
                }
            }
        }
        for (var i = 0; i<$scope.selectedParticipant.extraInfo.length; i++) {
            if ($scope.selectedParticipant.extraInfo[i] != undefined) {
                if ($scope.selectedParticipant.extraInfo[i].parameter == "true") {
                    $scope.selectedParticipant.extraInfo[i].parameter = true;
                    console.log("endret.. ");
                } else if ($scope.selectedParticipant.extraInfo[i].parameter == "false") {
                    $scope.selectedParticipant.extraInfo[i].parameter = false;
                    console.log("endret.. ");
                }
            }
        }
    };


    self.convertDates = function (dates) {
        var d = [];
        for (var i = 0; i < dates.length; i++) {
            d[i] = new Date(dates[i]);
        }
        return d;
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

    self.calculateTotal = function (payments, events) {
        var total = 0;
        if (payments) {
            for (var i = 0; i < payments.length; i++) {
                total += payments[i].amount;
            }
        }
        if (events) {
            for (var i = 0; i < events.length; i++) {
                total += events[i].price;
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

    $scope.getParameter = function(parameter){
        if (parameter == true){
            return "Ja"
        } else if (parameter == false) return "Nei"
        else return parameter;
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

    $scope.makeAccomondation = function () {
        if ($scope.checkboxAccModel.c1 == true) {
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
        if ($scope.selectedParticipant.accomondation !== null) {
            if (hotel.id == $scope.selectedParticipant.accomondation.hotelID) return true;
        }
        return false;
    };

    $scope.selectHotel = function (hotel) {
        $scope.selectedParticipant.accomondation.hotelID = hotel.id;
        $scope.chosenHotel = hotel;
    };

    $scope.updateRegistration = function (reg) {
        console.log(reg);
        if ($scope.checkboxAccModel.c1 == false) {
            reg.accomondation.id = 0;
            reg.accomondation.fromDate = null;
            reg.accomondation.toDate = null;
            console.log(reg);
        }
        if ($scope.selectedParticipant.accomondation.doubleroom == false) {
            $scope.selectedParticipant.accomondation.roommate = null;
        }
        reg.cost = findPrice($scope.selectedParticipant.dates.length, $scope.selectedParticipant.attendingFullCourse);
        var opt = self.inputParameterResolver($scope.selectedParticipant);
        reg.optionalPersonalia = opt.optionalPersonalia;
        reg.optionalWorkplace = opt.optionalWorkplace;
        reg.extraInfo = opt.extraInfo;
        attenderInfoService.updateRegistration(reg).then(function(success){
            $window.location.href = "/kursogkongress/personInfo";
        });
        //$window.location.href = "/kursogkongress/personInfo";
    };

    self.resolveInfo();

    self.inputParameterResolver = function (registration) {
        var optionalPersonalia = [];
        var optionalWorkplace = [];
        var extraInfo = [];
        for (var prop in registration.optionalPersonalia) { // Prop is indexnumber for the question in course.form.optionalPersonalia
            if (registration.optionalPersonalia[prop] != undefined) {
                var i = parseInt(prop);
                var inputParameter = {
                    parameter: registration.optionalPersonalia[prop].parameter,
                    type: $scope.course.form.optionalPersonalia[i].type
                };
                optionalPersonalia.push(inputParameter);
            }
        }
        for (var prop in registration.optionalWorkplace) { // Prop is indexnumber for the question in course.form.optionalWorkplace
            if (registration.optionalWorkplace[prop] != undefined) {
                var i = parseInt(prop);
                var inputParameter = {
                    parameter: registration.optionalWorkplace[prop].parameter,
                    type: $scope.course.form.optionalWorkplace[i].type
                };
                optionalWorkplace.push(inputParameter);
            }
        }
        for (var prop in registration.extraInfo) { // Prop is indexnumber for the question in course.form.extraInfo
            if (registration.extraInfo[prop] != undefined) {
                var i = parseInt(prop);
                var inputParameter = {
                    parameter: registration.extraInfo[prop].parameter,
                    type: $scope.course.form.extraInfo[i].type
                };
                extraInfo.push(inputParameter);
            }
        }
        console.log(optionalPersonalia);
        console.log(optionalWorkplace);
        console.log(extraInfo);
        return {optionalPersonalia: optionalPersonalia, optionalWorkplace: optionalWorkplace, extraInfo: extraInfo};
    };

    self.findPrice = function (ant, allDaysCheck) { // Finn ut hvilke dager han skal delta på, multipliser med course.dagpakke og course.kursavgift.
        var price = [];
        console.log($scope.dateArray.length + " dateArray.length");
        if (allDaysCheck == true || ant == $scope.dateArray.length) {
            price.push({amount: $scope.course.courseFee, description: 'Kursavgift'});
            for (var i = 0; i < $scope.dateArray.length; i++) {
                price.push({amount: $scope.course.dayPackage, description: 'Dagpakke'});
            }
        } else {
            for (var u = 0; u < ant; u++) {
                price.push({amount: $scope.course.courseSingleDayFee, description: 'Kursavgift Dag'});
                price.push({amount: ($scope.course.dayPackage), description: "Dagpakke"})
            }
        }
        return price;
    };
}]);

sessionRegisterApp.directive('ngConfirmClick', [
    function(){
        return {
            link: function (scope, element, attr) {
                var msg = attr.ngConfirmClick || "Er du sikker?";
                var clickAction = attr.confirmedClick;
                element.bind('click',function (event) {
                    if ( window.confirm(msg) ) {
                        scope.$eval(clickAction)
                    }
                });
            }
        };
    }]);