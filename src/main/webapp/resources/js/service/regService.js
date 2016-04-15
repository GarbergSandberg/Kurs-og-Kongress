app.factory('regService', ['$http', '$q', '$rootScope', function ($http, $q, $rootScope) {
    var form = {};
    var editCourse = undefined;
    var roles;
    var course;
    var days;
    var registrations = [{
        person: {
            personID: 0,
            firstname: 'Lars',
            lastname: 'Gar',
            birthYear: 1994,
            phonenumber: 93643247,
            email: 'la@ga.no',
            accomondation: null
        }
    },
        {
            person: {
                personID: 1,
                firstname: 'Eirik',
                lastname: 'Sand',
                birthYear: 1994,
                phonenumber: 93245342,
                email: 'ei@sa.no',
                accomondation: null
            }
        },
        {
            person: {
                personID: 2,
                firstname: 'Marius',
                lastname: 'Lauv',
                birthYear: 1991,
                phonenumber: 11111111,
                email: 'ma@la.no',
                accomondation: null
            }
        }];

    return {
        saveRoom: function (acc, first, second) {
            console.log("Her kommer registrasjonsinfo: ");
            if (second !== undefined) {
                for (var i = 0; i < registrations.length; i++) {
                    if (registrations[i].person.personID == first.personID) {
                        registrations[i].accomondation = angular.copy(acc);
                        registrations[i].accomondation.roommateID = second.personID;
                        console.log(registrations[i]);
                    }
                    if (registrations[i].person.personID == second.personID) {
                        registrations[i].accomondation = angular.copy(acc);
                        registrations[i].accomondation.roommateID = first.personID;
                        console.log(registrations[i]);
                    }
                }
            } else {
                for (var i = 0; i < registrations.length; i++) {
                    if (registrations[i].person.personID == first.personID) {
                        registrations[i].accomondation = angular.copy(acc);
                        registrations[i].accomondation.roommateID = first.personID;
                        console.log(registrations[i]);
                    }
                }
            }

        },

        removeRoom: function (person) {
            var p = {};
            for (var i = 0; i < registrations.length; i++) {
                if (registrations[i].person.personID == person.personID) {
                    p = registrations[i].accomondation.roommateID;
                    registrations[i].accomondation = undefined;
                }
            }
            for (var i = 0; i < registrations.length; i++) {
                if (registrations[i].person.personID == p) {
                    registrations[i].accomondation = undefined;
                }
            }
        },

        hasRoommate: function (person) {
            for (i = 0; i < registrations.length; i++) {
                if (registrations[i].person == person) {
                    if (registrations[i].accomondation !== undefined) {
                        return true;
                    }
                }
            }
            return false;
        },

        getPersonName: function (id) {
            for (var i = 0; i < registrations.length; i++) {
                if (registrations[i].person.personID == id) {
                    if (registrations[i].accomondation != null) {
                        return registrations[i].person.firstname + " " + registrations[i].person.lastname;
                    }

                }
            }
            return null;
        },

        getPersonsRegistration: function (id) {
            for (var i = 0; i < registrations.length; i++) {
                if (registrations[i].person.personID == id) {
                    return registrations[i];
                }
            }
            return null;
        },

        get: function () {
            return registrations;
        },

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
                params: indata
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
                        return response.data;
                    },
                    function (errResponse) {
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