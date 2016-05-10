/**
 * Created by Lars on 15.04.2016.
 */
sessionRegisterApp.factory('statisticsService', ['$http', '$q','$rootScope', function($http, $q, $rootScope) {
    var statisticsService = {};

    statisticsService.getCountRegistrations = function(courseID){
        return $http.get('getCountRegistrations', {params: {course_id: courseID}})
            .then(
                function(success){
                    var i = success.data;
                    console.log("::::::: " + success.data);
                    console.log("::::::: " + i);
                    return i;
                },
                function(error){
                    console.log("Feil i getAntRegistrations" + error);
                }
            )

    };

    statisticsService.getRegistrations = function(courseID){
        return $http.get('getRegistrations', {params: {course_id: courseID}})
            .then(
                function (success) {
                    return success.data;
                },
                function (error) {
                    console.error('Error while retrieving registrations');
                }
            );
    };

    statisticsService.getEmails = function(courseID){
        return $http.get('getEmails', {params: {course_id: courseID}})
            .then(
                function (success) {
                    return success.data;
                },
                function (error) {
                    console.error('Error while retrieving emails');
                }
            );
    };

    statisticsService.setSessionStorageID = function(sessionID){
        console.log(sessionID + " = personID before encryption");
        return $http.get('setSessionStorageID', {params: {id: sessionID}})
            .then(
                function (success) {
                    console.log(success.data.toString() + " = personID afterEncryption");
                    sessionStorage.selectedPerson = success.data;
                },
                function (error) {
                    console.error('Error setting sessionStorageID');
                    console.log(error);
                }
            );
    };

    statisticsService.getSessionStorageID = function(sessionID){
        console.log(sessionID + " = personID sent from client (encrypted)");
        return $http.get('getSessionStorageID', {params: {id: sessionID}})
            .then(
                function (success) {
                    console.log(success.data + " = personID after decryption");
                    return success.data;
                },
                function (error) {
                    console.error('Error getting sessionStorageID');
                }
            );
    };

    statisticsService.getNumberOfPayments = function(regID, descr){
        return $http.get('getNumberOfPayments', {params: {registration_id: regID, description: descr}})
            .then(
            function(success){
                return success.data;
            }, function(error){
                console.log("Error in getNumberOfPayments: " + error);
            }
        )
    };

    statisticsService.getNumberOfEvents = function(eventID){
        return $http.get('getNumberOfEvents', {params: {event_id: eventID}})
            .then(
            function(success){
                return success.data;
            }, function(error){
                console.log("Error in getNumberOfPayments: " + error);
            }
        )
    };

    return statisticsService;
}]);