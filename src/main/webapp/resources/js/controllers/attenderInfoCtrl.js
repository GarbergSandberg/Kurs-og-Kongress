/**
 * Created by eiriksandberg on 07.04.2016.
 */
sessionRegisterApp.controller('attenderInfoCtrl', ['$scope', 'attenderInfoService', 'statisticsService', '$window', function ($scope, attenderInfoService, statisticsService, $window) {
    $scope.tabs = [
        {title:'Oversikt', content:'resources/jsp/participantInfo.jsp'},
        {title:'Fakturering', content:'resources/jsp/invoice.jsp'}
    ];
    $scope.tabs.activeTab = 'Oversikt';
    $scope.registrations = [];
    $scope.selectedParticipant = {};
    $scope.dateArray = [];
    $scope.selectedDays = [];
    $scope.course = {};

    $scope.showInfo = function(registration){
        if (registration !== undefined){
            self.setSessionID(registration.person.personID);
        } else {
            $window.location.href = "/kursogkongress/personInfo";
        }
    };
    $scope.showInvoice = function(){
        $window.location.href = "/kursogkongress/invoice";
    };
    $scope.showInvoiceFromList = function(registration){
        self.setSessionIDFromList(registration.person.personID);
    };

    self.resolveInfo = function() {
        var sid = sessionStorage.selectedPerson;
        if(sid !== undefined){
            attenderInfoService.getSessionStorageID(sid).then(function(success){
                $scope.selectedParticipant = self.findPerson(success);
                $scope.selectedParticipant.attendingSessions = self.findSessions($scope.selectedParticipant);
                $scope.selectedParticipant.attendingEvents = self.findEvents($scope.selectedParticipant);
                $scope.selectedParticipant.attendingFullCourse = self.isAttendingFullCourse($scope.selectedParticipant);
                $scope.selectedParticipant.totalAmount = self.calculateTotal($scope.selectedParticipant.cost);
                $scope.course = $scope.selectedParticipant.course;
                $scope.selectedDays = $scope.selectedParticipant.dates;
                console.log($scope.selectedParticipant);
            }, function(error){
                console.log("Noe gikk galt her");
            });
        }
    };

    self.findPerson = function(id){
        for (var i = 0; i < $scope.registrations.length; i++){
            if ($scope.registrations[i].person.personID == id){
                return $scope.registrations[i];
            }
        }
    };

    self.findSessions = function (registration) {
        var sessionArray = [];
        if (registration.sessionsToAttend !== null){
            for (var i = 0; i < registration.sessionsToAttend.length; i++){
                var u = registration.sessionsToAttend[i];
                for (var x = 0; x < registration.course.sessions.length; x++){
                    if (u == registration.course.sessions[x].id){
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
        if (registration.eventsToAttend !== null){
            for (var i = 0; i < registration.eventsToAttend.length; i++){
                var u = registration.eventsToAttend[i];
                for (var x = 0; x < registration.course.events.length; x++){
                    if (u == registration.course.events[x].id){
                        eventArray.push(registration.course.events[x]);
                        break;
                    }
                }
            }
        }
        return eventArray;
    };

    self.getSessions = function(cid){
        $scope.course = courseService.getCourse(cid);
    };

    self.getReg = function(){ //
        var cid = sessionStorage.selectedCourse;
        if (cid !== undefined){
            attenderInfoService.getRegistrations(cid).then(function(success){
                console.log("Length: " + success.length);
                self.mapRegistration(success);
                self.resolveInfo();
            }, function(error){
                console.log("Error");
            });
        } else console.log("sid = undefined");
    };

    self.mapRegistration = function(registrations){
        for (var i = 0; i < registrations.length; i++){
            var registration = registrations[i];
            if (registration != null){
                $scope.registrations.push(registration);
            }
        }
    };

    self.isAttendingFullCourse = function(registration){
        var courseLength = self.getDates(new Date(registration.course.startDate), new Date(registration.course.endDate)).length;
        var daysAttending = registration.dates.length;
        if(courseLength == daysAttending){
            return true;
        } else {
            return false;
        }
    };

    Date.prototype.addDays = function(days) {
        var dat = new Date(this.valueOf());
        dat.setDate(dat.getDate() + days);
        return dat;
    };

    self.getDates = function(startDate, stopDate) {
        var currentDate = startDate;
        while (currentDate <= stopDate) {
            $scope.dateArray.push(currentDate);
            currentDate = currentDate.addDays(1);
        }
        return $scope.dateArray;
    };

    self.calculateTotal = function(registration){
        var total = 0;
        if(registration !== undefined){
            for (var i = 0; i < registration.length; i++){
                total += registration[i].amount;
            }
        }
        return total;
    };

    self.setSessionID = function(id){
        attenderInfoService.setSessionStorageID(id).then(function(successCallback){
            $window.location.href = "/kursogkongress/personInfo";
            console.log("ok. ");
        }, function(errorCallback){
            console.log("error in setSessionID");
        });
    };

    self.setSessionIDFromList = function(id){
        attenderInfoService.setSessionStorageID(id).then(function(successCallback){
            $window.location.href = "/kursogkongress/invoice";
        }, function(errorCallback){
            console.log("error in setSessionIDFromList");
        });
    };
    self.getReg();

    $scope.changeRegistration = function(){
        console.log("Setter change = true");
        $scope.change = !$scope.change;
    };

    $scope.sameDate = function(d1, n2){
        var d2 = new Date(n2);
        if ((d1.getFullYear() == d2.getFullYear()) && d1.getDate() == d2.getDate()){
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

    $scope.selectSession = function(session) {
        var idx = $scope.selectedParticipant.attendingSessions.indexOf(session);
        if (idx > -1) { // Blir unchecked.
            $scope.selectedParticipant.attendingSessions.splice(idx, 1);
        } else {
            var notOverlaps = true;
            for (i = 0; i<$scope.selectedParticipant.attendingSessions; i++){
                if ($scope.overlaps(session.startTime, session.endTime, $scope.selectedParticipant.attendingSessions[i].startTime, $scope.selectedParticipant.attendingSessions[i].endTime)){
                    notOverlaps = false;
                    break;
                }
            } if (notOverlaps){
                $scope.selectedParticipant.attendingSessions.push(session);
            }
        }
    };

    $scope.overlaps = function(startA, endA, startB, endB) { // Hvis overlapper, return true. else false.
        if (startA <= startB && startB <= endA) return true; // b starts in a
        if (startA <= endB   && endB   <= endA) return true; // b ends in a
        if (startB <  startA && endA   <  endB) return true; // a in b
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

    $scope.selectEvent = function(event) {
        var idx = $scope.selectedParticipant.attendingEvents.indexOf(event);
        if (idx > -1) { // Blir unchecked.
            $scope.selectedParticipant.attendingEvents.splice(idx, 1);
        } else {
            var notOverlaps = true;
            for (i = 0; i<$scope.selectedParticipant.attendingEvents; i++){
                if ($scope.overlaps(session.startTime, session.endTime, $scope.selectedParticipant.attendingEvents[i].startTime, $scope.selectedParticipant.attendingEvents[i].endTime)){
                    notOverlaps = false;
                    break;
                }
            } if (notOverlaps){
                $scope.selectedParticipant.attendingEvents.push(event);
            }
        }
    };

    $scope.wholeCourse = function(checked) {
        if(checked == true){
            $scope.selectedDays = [];
            for (var i = 0; i<$scope.dateArray.length; i++){
                $scope.selectedDays.push($scope.dateArray[i]);
                console.log(i + " Er checked: " + $scope.selectedDays[i]);
            }
        } else{
            $scope.selectedDays = [];
            console.log("Tabellen er tom")
        }
        $scope.selectedParticipant.dates = $scope.selectedDays;
    };

    $scope.selectDay = function(day){
        $scope.allDaysCheck = false;
        for(var i = 0; i < $scope.selectedDays.length; i++){
            if (day == $scope.selectedDays[i]){
                $scope.selectedDays.splice(i,1);
                console.log(day + " er fjernet. Lengden på tabell er nå " + $scope.selectedDays.length);
                $scope.selectedParticipant.dates = $scope.selectedDays;
                return;
            }
        }
        $scope.selectedDays.push(day);
        console.log(day + " er pushet. Lengden på tabell er nå " + $scope.selectedDays.length);
        if ($scope.selectedDays.length == $scope.dateArray.length){
            $scope.allDaysCheck = true;
        }
        $scope.selectedParticipant.dates = $scope.selectedDays;
    };

}]);