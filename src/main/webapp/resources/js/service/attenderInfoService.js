/**
 * Created by eiriksandberg on 07.04.2016.
 */
sessionRegisterApp.factory('attenderInfoService', ['$http','$q', '$rootScope', function($http, $q, $rootScope){
    var attenderInfoService = {};

    attenderInfoService.updateRegistration = function(reg){
        return $http.post('updateRegistration', reg)
            .then(
                function (success) {
                    console.log("UpdateReg gikk bra.");
                    return success.data;
                },
                function (error) {
                    return $q.reject(error.data);
                }
            );
    };

    attenderInfoService.deleteRegistration = function(reg){
        return $http.post('deleteRegistration', reg)
            .then(
                function (success) {
                    console.log("Sletting gikk bra");
                    return true;
                },
                function (error) {
                    return false;
                }
            );
    };

    attenderInfoService.getRegistrations = function(courseID){
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

    attenderInfoService.getCourse = function (courseID) {
        return $http.get('getCourse', {params: {course_id: courseID}})
            .then(
                function (response) {
                    return response.data;
                },
                function (errResponse) {
                    return $q.reject(errResponse.data);
                }
            );
    },

    attenderInfoService.setSessionStorageID = function(registration){
        console.log(registration.registrationID + " = registrationID before encryption");
        return $http.get('setSessionStorageID', {params: {id: registration.registrationID}})
            .then(
                function (success) {
                    console.log(success.data.toString() + " = registrationID afterEncryption");
                    sessionStorage.selectedPerson = success.data;
                },
                function (error) {
                    console.error('Error setting sessionStorageID');
                    console.log(error);
                }
            );
    };

    attenderInfoService.getSessionStorageID = function(sessionID){
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

    attenderInfoService.getRegistration = function(registrationID){
        return $http.get('getRegistration', {params: {registration_id: registrationID}})
            .then(
                function (success) {
                    console.log(success.data);
                    return success.data;
                },
                function (error) {
                    console.error('Error while retrieving registrations');
                }
            );
    };

    attenderInfoService.getGroupNumberOfPayments = function(idGroup, descr){
        return $http.get('getGroupNumberOfPayments', {params: {idGroupregistration: idGroup, description: descr}})
            .then(
                function(success){
                    return success.data;
                }, function(error){
                    console.log("Error in getGroupNumberOfPayments: " + error);
                }
            )
    };

    attenderInfoService.getGroupNumberOfEvents = function(idGroupregistration, eventID){
        return $http.get('getGroupNumberOfEvents', {params: {idGroupregistration: idGroupregistration, event_id: eventID}})
            .then(
                function(success){
                    return success.data;
                }, function(error){
                    console.log("Error in getNumberOfPayments: " + error);
                }
            )
    };
    return attenderInfoService;
}]);