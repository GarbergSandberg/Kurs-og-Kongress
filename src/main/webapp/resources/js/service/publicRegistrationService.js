/**
 * Created by eiriksandberg on 25.04.2016.
 */
publicRegistrationApp.factory('publicRegistrationService', ['$http', '$q','$rootScope', function($http, $q, $rootScope) {
    return {

        getPublicCourses: function (callback) {
            return $http.get('getPublicCourses')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while getCourses');
                        return $q.reject(errResponse.data);
                    }
                );
        },
        setSessionStorageID: function (courseID) {
            console.log(courseID + " = courseID before encryption");
            return $http.get('setSessionStorageID', {params: {id: courseID}})
                .then(
                    function (success) {
                        console.log(success.data.toString() + " = courseID afterEncryption");
                        sessionStorage.cid = success.data;
                    },
                    function (error) {
                        console.error('Error setting sessionStorageID');
                        console.log(error);
                    }
                );
        },

        getSessionStorageID: function (courseID) {
            console.log(courseID + " = courseID sent from client (encrypted)");
            return $http.get('getSessionStorageID', {params: {id: courseID}})
                .then(
                    function (success) {
                        console.log(success.data + " = courseID after decryption");
                        return success.data;
                    },
                    function (error) {
                        console.error('Error getting sessionStorageID');
                    }
                );
        },

        getCountRegistrations: function(courseID){
        return $http.get('getCountRegistrations', {params: {course_id: courseID}})
            .then(
                function(success){
                    console.log("success.data = " + success.data);
                    return success.data;
                },
                function(error){
                    console.log("Error in getCountRegistrations " + error);
                }

            );
        }
    }
}]);