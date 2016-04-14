app.factory('regService', ['$http', '$q', '$rootScope', function ($http, $q, $rootScope) {
    var form = {};
    var editCourse = undefined;
    var roles;
    var course;
    var days;


    return {
        sendForm: function (form) { //to be deleted
            var indata = {
                optionalPersonalia: form.optionalPersonalia,
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

        setCourse: function (newCourse, newRoles, newDays) {
            $rootScope.$broadcast('courseSet', newCourse);
            $rootScope.$broadcast('dateSet', newDays);
            $rootScope.$broadcast('rolesSet', newRoles);
            $rootScope.$broadcast('formSet', form);
            course = newCourse;
            roles = newRoles;
            days = newDays;
        },

        prepareForm: function () {
            $rootScope.$broadcast('prepareForm');
        },

        setForm: function (newForm) {
            form = newForm;
        },

        getForm: function () {
            return form;
        },

        setRecievedForm: function (form) {
            $rootScope.$broadcast('recievedForm', form);
        },

        sendRegistration: function (registration) {
            console.log(registration);
            return $http.post('saveReg', registration)
                .then(
                    function (response) {
                        console.log("Success!");
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },

        sendPerson: function (person) {
            console.log(person);
            return $http.post('sendPerson', person)
                .then(
                    function (response) {
                        console.log("Success!");
                        return response.data;
                    },
                    function (errResponse) {
                        console.log("Error in sendPerson (service)");
                        return $q.reject(errResponse.data);
                    }
                );
        },

        sendInfo: function (newcourse) {
            return $http.post('saveinformation_json', newcourse)
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

        getMockCourse: function (callback) {
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

        getCourses: function (callback) {
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

        getCourse: function (courseID) {
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

        getTemplate: function (callback) {
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