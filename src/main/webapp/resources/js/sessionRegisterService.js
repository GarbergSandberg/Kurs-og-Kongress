/**
 * Created by eiriksandberg on 29.01.2016.
 */

myApp.factory('sessionService', function () {
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
            sessionService.add(newSession);
        }
    }

    sessionService.date = function (btnDate) {
        currentDate = btnDate;
    }

    sessionService.add = function (newSession) {
        newSession.id = generateId();
        newSession.date = currentDate;
        sessions.push(newSession);
        console.log("Pushed object = " + newSession.date);
    }

    sessionService.delete = function (newSession) {
        for (var i = 0; i < sessions.length; i++) {
            if (sessions[i].id == newSession.id) {
                console.log("Har nå funnet eventet som skal bli slettet. Nr: " + i);
                sessions.splice(i, 1);
                console.log("Objektet er slettet... Håper jeg.. ");
            } else {
                console.log("Objektet finnes ikke. ");
            }
        }
    }


    sessionService.get = function () {
        return sessions;
    };

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
                for (var property in newSession) {
                    console.log(property + "=" + newSession[property]); // Test. Remove!!
                }
                for (var i = 0; i < sessions.length; i++) {
                    if (sessions[i].id == newSession.id) {
                        oldSession.exists = true;
                        oldSession.index = i;
                        console.log(oldSession);
                    }
                }
            }
        }
        return oldSession;
    }

    function sessionUpdate(session, index) {
        for (var prop in session) {
            console.log("property in prop = " + prop);                  // remove console.log after testing is done
            console.log("session[prop] = " + session[prop]);
            if (session[prop] != undefined) {
                console.log("sessionUpdate = " + sessions[index][prop] + " = " + session[prop]);
                sessions[index][prop] = session[prop];
            }
        }
    }

    return sessionService;
});