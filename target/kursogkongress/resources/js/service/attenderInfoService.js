/**
 * Created by eiriksandberg on 07.04.2016.
 */
sessionRegisterApp.factory('attenderInfoService', ['$http','$q', '$rootScope', function($http, $q, $rootScope){
    var attenderInfoService = {};

    attenderInfoService.updateRegistration = function(reg){
        console.log("2");
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
        console.log(registration.person.personID + " = personID before encryption");
        return $http.get('setSessionStorageID', {params: {id: registration.person.personID}})
            .then(
                function (success) {
                    console.log(success.data.toString() + " = personID afterEncryption");
                    sessionStorage.selectedPerson = success.data;
                    sessionStorage.selectedCourse = registration.course.id;
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
}]);