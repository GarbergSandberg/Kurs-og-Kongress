/**
 * Created by eiriksandberg on 03.02.2016.
 */

myApp.factory('courseService', ['$http', '$q','$rootScope', function($http, $q, $rootScope) {
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
                        return $q.reject(errResponse.data);
                    }
                );
        },

        sendForm: function (form) { //to be deleted
            console.log(form.optionalPersonalia);
            var indata = {
                requiredPersonalia: form.requiredPersonalia,
                optionalPersonalia: form.optionalPersonalia,
                requiredWorkplace: form.requiredWorkplace,
                optionalWorkplace: form.optionalWorkplace,
                extraInfo: form.extraInfo,
                checkboxModel: form.checkboxModel
            };
            return $http({
                url: "form",
                method: "POST",
                params:indata
            })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while sending form');
                        return $q.reject(errResponse.data);
                    }
                );
        },

        sendInputParameter: function (input) { //to be deleted
            console.log(input);
            return $http.post('inputparameter', input)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while sendingInfo');
                        return $q.reject(errResponse.data);
                    }
                );
        },

        getMockCourse: function(callback){
            return $http.get('getCourseMock')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while getMockCourse');
                        return $q.reject(errResponse.data);
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
                        console.log(response.data);
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while getCourse');
                        return $q.reject(errResponse.data);
                    }
                );
        },

        getTemplate: function(callback){
            return $http.get('getTemplate')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while getting template');
                        return $q.reject(errResponse.data);
                    }
                );
        }
    }
}]);

