/**
 * Created by eiriksandberg on 03.02.2016.
 */

sessionRegisterApp.factory('courseService', ['$http', '$q','$rootScope', function($http, $q, $rootScope) {
    var form = {};

    return {
        prepareForm: function(){
            $rootScope.$broadcast('prepareForm');
        },

        setForm: function(newForm){
            form = newForm;
        },

        getForm: function(){
            return form;
        },

        setRecievedForm: function(form){
            $rootScope.$broadcast('recievedForm', form);
        },

        sendInfo: function (course) {
            console.log(course);
            return $http.post('saveinformation_json', course)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while sendingInfo');
                    }
                );
        },

        getCourses: function(callback){
            return $http.get('getCourses')
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

        getCourse: function(courseID){
            return $http.get('getCourse', {params: {course_id: courseID}})
                .then(
                    function (response) {
                        console.log("HER: ");
                        console.log(response.data);
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while getCourse');
                        return $q.reject(errResponse.data);
                    }
                );
        }
    }
}]);

