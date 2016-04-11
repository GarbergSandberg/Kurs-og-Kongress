/**
 * Created by eiriksandberg on 07.04.2016.
 */
attenderInfoApp.factory('attenderInfoService', function($http, $rootScope){
    var attenderInfoService = {};

    attenderInfoService.getRegistrations = function(courseID){
        console.log(courseID + " = courseID");
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

    attenderInfoService.setSessionStorageID = function(sessionID){
        console.log(sessionID + " = personID before encryption");
        return $http.get('setSessionStorageID', {params: {id: sessionID}})
            .then(
                function (success) {
                    console.log(success.data + " = personID afterEncryption");
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

    return attenderInfoService;
});