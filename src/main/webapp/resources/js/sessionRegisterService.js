/**
 * Created by eiriksandberg on 29.01.2016.
 */

myApp.factory('sessionService',['$rootScope', function ($rootScope) {
    var dates = {};
    var sessions = [];
    var sessionService = {};
    var currentDate = "";

    sessionService.save = function (newSession) {
        var old = sessionExists(newSession);
        console.log(old.exists + " Exists");
        if (old.exists) {
            sessionUpdate(newSession, old.index)
            console.log("Modified object = ")
            for (var property in sessions[old.index]) {
                console.log(property + "=" + sessions[old.index][property]); // Test. Remove!!
            }

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
    }

    sessionService.date = function (btnDate) {
        currentDate = new Date(btnDate);
    }

    sessionService.setDates = function (datesToBeSet) {
        dates = datesToBeSet;
        $rootScope.$broadcast('dates:updated', dates);
    }

    sessionService.getDates = function(){
        return dates;
    }

    sessionService.add = function (newSession) {
        newSession.id = generateId();
        newSession.date = currentDate;
        sessions.push(newSession);
        console.log("Pushed object = " + newSession.date);
    };

    sessionService.delete = function (newSession) {
        for (var i = 0; i < sessions.length; i++) {
            if (sessions[i].id == newSession.id) {
                sessions.splice(i, 1);
            } else {
                console.log("Objektet finnes ikke. ");
            }
        }
    }


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
                if(prop == "repetitiveSession"){
                    for (item in session[prop]){
                        addDuplicatedSessions(session);
                        session.repetitiveSession = {};
                    }
                }
                sessions[index][prop] = session[prop];
            }
        }
    }

    return sessionService;
}]);