/**
 * Created by eiriksandberg on 29.01.2016.
 */

sessionRegisterApp.factory('sessionService',['$rootScope', function ($rootScope) {
    var dates = {};
    var sessions = [];
    var sessionService = {};
    var currentDate = "";
    var temporaryIDs = [];

    sessionService.save = function (newSession) {
        var old = sessionExists(newSession);
        console.log(old.exists + " Exists");
        if (old.exists) {
            sessionUpdate(newSession, old.index);
        } else {
            if(newSession.repetitiveSession != undefined) {
                sessionService.add(newSession);
                addDuplicatedSessions(newSession);
                newSession.repetitiveSession = {};
            }
            else{
                sessionService.add(newSession);
            }
        }
        for (var i = 0; i < sessions.length; i++){
            checkIfOverlaps(sessions[i]);
        }
    };

    sessionService.setSessions = function(sessionsSent){
        for (var i = 0; i < sessionsSent.length; i++){
            var newSession = sessionsSent[i];
            newSession.id = sessionsSent[i].id;
            newSession.startTime = new Date(sessionsSent[i].startTime);
            newSession.endTime = new Date(sessionsSent[i].endTime);
            newSession.date = new Date(sessionsSent[i].date);
            newSession.hourMinuteStart = convertDate(newSession.startTime.getHours(),newSession.startTime.getMinutes());
            newSession.hourMinuteEnd = convertDate(newSession.endTime.getHours(),newSession.endTime.getMinutes());
            newSession.overlap = false;
            sessions.push(newSession);
        }
        for (var i = 0; i < sessions.length; i++){
            checkIfOverlaps(sessions[i]);
        }
    };

    sessionService.date = function (btnDate) {
        currentDate = new Date(btnDate);
    };

    sessionService.setDates = function (datesToBeSet) {
        dates = datesToBeSet;
        $rootScope.$broadcast('dates:updated', dates);
    };

    sessionService.getDates = function(){
        return dates;
    };

    sessionService.add = function (newSession) {
        newSession.id = generateId();
        newSession.date = currentDate;
        newSession.hourMinuteStart = convertDate(newSession.startTime.getHours(),newSession.startTime.getMinutes());
        newSession.hourMinuteEnd = convertDate(newSession.endTime.getHours(),newSession.endTime.getMinutes());
        newSession.overlap = false;
        sessions.push(newSession);
        console.log("Pushed object = " + newSession.date);
    };

    sessionService.delete = function (newSession) {
        for (var i = 0; i < sessions.length; i++) {
            if (sessions[i].id == newSession.id) {
                sessions.splice(i, 1);
                break;
            } else {
                console.log("Objektet finnes ikke. ");
            }
        }
        for (var i = 0; i < sessions.length; i++){
            checkIfOverlaps(sessions[i]);
        }
    };


    sessionService.get = function () {
        return sessions;
    };

    function addDuplicatedSessions(session) {
        if(session.repetitiveSession != undefined) {
            for (var item in session.repetitiveSession) {
                var duplicatedSession = angular.copy(session);
                currentDate = new Date(item);
                duplicatedSession.repetitiveSession = {};
                sessionService.add(duplicatedSession);
            }
        }
    }

    function generateId() {
        var highestId = 0;
        for (var i = 0; i < sessions.length; i++) {
            if (sessions[i].id >= highestId) {
                highestId = sessions[i].id;
            }
        }
        var id = highestId + 1;
        temporaryIDs.push(id);
        return id;
    }

    function sessionExists(newSession) {
        var oldSession = new Object();
        oldSession.exists = false;
        if (typeof(newSession) !== 'undefined') {
            if (sessions.length > 0) {
                for (var i = 0; i < sessions.length; i++) {
                    if (sessions[i].id == newSession.id) {
                        oldSession.exists = true;
                        oldSession.index = i;
                    }
                }
            }
        }
        return oldSession;
    }

    function sessionUpdate(session, index) {
        for (var prop in session) {
            if (session[prop] != undefined) {
                if (prop == "repetitiveSession") {
                    for (item in session[prop]) {
                        addDuplicatedSessions(session);
                        session.repetitiveSession = {};
                    }
                }
                sessions[index][prop] = session[prop];
            }
        }
        sessions[index].hourMinuteStart = convertDate(session.startTime.getHours(), session.startTime.getMinutes());
        sessions[index].hourMinuteEnd = convertDate(session.endTime.getHours(), session.endTime.getMinutes());
        for (var i = 0; i < sessions.length; i++){
            checkIfOverlaps(sessions[i]);
        }
    }


        function convertDate(hours, minutes) {
            var hoursString = hours.toString();
            if (hoursString < 10 ){
                hoursString = '0'+hoursString;
            }
            var minutesString = minutes.toString();
            if (minutesString < 10 ){
                minutesString = '0'+minutesString;
            }
            var string = hoursString + minutesString;
            var number = parseInt(string);
            return number;
        }

    function overlapsTest(startA, endA, startB, endB) { // Hvis overlapper, return true. else false.
        if (startA <= startB && startB < endA) return true; // b starts in a
        if (startA < endB   && endB   <= endA) return true; // b ends in a
        if (startB <  startA && endA   <  endB) return true; // a in b
        return false;
    };

    function overlaps(A,B){
        return overlapsTest(A.hourMinuteStart, A.hourMinuteEnd, B.hourMinuteStart, B.hourMinuteEnd);
    }

    function checkIfOverlaps(session){
            for (var u = 0; u < sessions.length; u++){
                if(session.id != sessions[u].id){
                    console.log("Date det jeg legger til: " + session.date.getDate() + " Det jeg sjekker: " + sessions[u].date.getDate());
                    if(session.date.getDate() == sessions[u].date.getDate()){
                        if(overlaps(session, sessions[u])){
                            session.overlap = true;
                            return
                        }
                    }
                }
            }
            session.overlap = false;
    };

    sessionService.destroyTempIDs = function(){
        if (temporaryIDs.length > 0){
            for (var i = 0; i < temporaryIDs.length; i++){
                for (var u = 0; u < sessions.length; u++){
                    if(temporaryIDs[i] == sessions[u].id){
                        console.log("Fjerner id på sesjon " + sessions[u].id);
                        sessions[u].id = -1;
                    }
                }
            }
        }
    };

    function dateWithoutHours(date){
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        if(month < 10){
            month = "0"+month;
        }
        var day = date.getDate();
        if(day < 10){
            day = "0"+day;
        }
        var dateString = year+"-"+month+"-"+day;
        console.log("DateString: " + dateString);
        return new Date(dateString);
    }

    return sessionService;
}]);