/**
 * Created by eiriksandberg on 21.01.2016.
 */
var myApp = angular.module('registerApp', ['ngAnimate', 'mgcrea.ngStrap', 'ngRoute']);

myApp.controller('AddSessionCtrl', ['$scope','$modal' ,'sessionService', function($scope, $modal, sessionService){
    var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerSessionModal.html', show: false});
    $scope.showModal = function() {
        myModal.$promise.then(myModal.show);
    };
    $scope.hideModal = function() {
        myModal.$promise.then(myModal.show(false));
    };
    $scope.sessions = sessionService.get();
}]);


myApp.controller('AddInfoCtrl', ['$scope', 'sessionService', function($scope, sessionService){
    $scope.sessions = sessionService.get();
    $scope.update = function(newSession){
        sessionService.save(newSession);
    }
}]);

myApp.factory('sessionService', function() {
    var sessions = [];
    var sessionService = {};

    sessionService.save = function(newSession){
        var old = sessionExists(newSession);
        console.log(old.exists + " Exists");
        if (old.exists){
            sessionUpdate(newSession, old.index)
            console.log("Modified object = ")
            for(var property in sessions[old.index]) {
                console.log(property + "=" + sessions[old.index][property]); // Test. Remove!!
            }

        } else{
            sessionService.add(newSession);
        }
    }

    sessionService.add = function(newSession) {
        newSession.id = generateId();
            sessions.push(newSession);
            console.log("Pushed object = " + newSession.title);
        }

    sessionService.get = function() {
        return sessions;
    };

    function generateId(){
        var highestId = 0;
        for (var i = 0; i < sessions.length; i++){
            if (sessions[i].id >= highestId){
                highestId = sessions[i].id;
            }
        }
        var id = highestId + 1;
        return id;
    }

    function sessionExists(newSession){
        var oldSession = new Object();
        oldSession.exists = false;
        if (typeof(newSession) !== 'undefined'){
            if(sessions.length > 0){
                for(var property in newSession) {
                    console.log(property + "=" + newSession[property]); // Test. Remove!!
                }
                for(var i = 0; i < sessions.length; i++){
                    if(sessions[i].id == newSession.id){
                        oldSession.exists = true;
                        oldSession.index = i;
                        console.log(oldSession);
                    }
                }
            }
        }
        return oldSession;
    }

    function sessionUpdate(session, index){
        for(var prop in session) {
            console.log("property in prop = " + prop);
            console.log("session[prop] = " + session[prop]);
            if(session[prop] != undefined){
                console.log("sessionUpdate = " + sessions[index][prop] + " = " + session[prop]);
                sessions[index][prop] = session[prop];
            }
        }
    }

    return sessionService;
});
