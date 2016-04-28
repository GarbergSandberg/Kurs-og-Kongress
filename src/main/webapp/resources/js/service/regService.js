app.factory('regService', ['$http', '$q', '$rootScope', function ($http, $q, $rootScope) {
    var form = {};
    var editCourse = undefined;
    var roles;
    var course;
    var days;
    var registrations = [];
    //var reg = {role: 'Midtbane', person: {firstname: 'Lars', lastname: 'gar', email: 'la@ga.no', number: '993322', birthYear: '1994'}, gender: 'male'};
    //registrations.push(reg);

    var persons = [];
    var hasRoom = [];

    return {
        saveRoom: function (acc, first, second) {
            var newAcc = {hotelID: {}, roommate: {}, toDate: {}, fromDate: {}, doubleroom: {}};
            console.log("Her kommer acc: sammenlign med hva du gjør i saveRoom. ");
            console.log(acc);
            if (second !== undefined) {
                for (var i = 0; i < registrations.length; i++) {
                    if (registrations[i].person.personID == first.personID) {
                        registrations[i].accomondation = angular.copy(newAcc);
                        registrations[i].accomondation.hotelID = acc.selectedAccomondation.id;
                        registrations[i].accomondation.doubleroom = acc.doubleroom;
                        console.log(acc.fromDate);
                        console.log(new Date(acc.fromDate));
                        registrations[i].accomondation.fromDate = new Date(acc.fromDate);
                        registrations[i].accomondation.toDate = new Date(acc.toDate);
                        registrations[i].accomondation.roommateID = second.personID;
                        hasRoom.push(first.personID);
                    }
                    if (registrations[i].person.personID == second.personID) {
                        registrations[i].accomondation = angular.copy(newAcc);
                        registrations[i].accomondation.hotelID = acc.selectedAccomondation.id;
                        registrations[i].accomondation.doubleroom = acc.doubleroom;
                        registrations[i].accomondation.fromDate = acc.fromDate;
                        registrations[i].accomondation.toDate = acc.toDate;
                        registrations[i].accomondation.roommateID = first.personID;
                        hasRoom.push(second.personID);
                    }
                }
            } else {
                for (var i = 0; i < registrations.length; i++) {
                    if (registrations[i].person.personID == first.personID) {
                        registrations[i].accomondation = angular.copy(newAcc);
                        registrations[i].accomondation.doubleroom = acc.doubleroom;
                        registrations[i].accomondation.roommateID = first.personID;
                        registrations[i].accomondation.hotelID = acc.selectedAccomondation.id;
                        registrations[i].accomondation.fromDate = acc.fromDate;
                        registrations[i].accomondation.toDate = acc.toDate;
                        hasRoom.push(first.personID);
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
                    var idx = hasRoom.indexOf(person.personID);
                    hasRoom.splice(idx, 1);
                }
            }
            for (var i = 0; i < registrations.length; i++) {
                if (registrations[i].person.personID == p) {
                    var idx = hasRoom.indexOf(p);
                    hasRoom.splice(idx, 1);
                    registrations[i].accomondation = undefined;
                }
            }

        },

        hasRoommate: function (person) {
            for (var i = 0; i < registrations.length; i++) {
                if (registrations[i].person == person) {
                    if (registrations[i].accomondation !== undefined) {
                        return true;
                    }
                }
            }
            return false;
        },

        getHasRoom: function () {
            return hasRoom;
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

        saveReg: function (person, numberOfPersons) { // Gets an Array of persons, checks if they exist already, if so theyre updated. If not, they are added.
            console.log("person.length (arrayen sendt inn): " + person.length + ",  numberOfPersons: " + numberOfPersons);
            for (var i = 0; i < numberOfPersons; i++) { // egentlig person.length  .. byttet til numberOfPersons
                var old = personExists(person[i]);
                if (old.exists) {
                    personUpdate(person[i], old.index);
                } else {
                    addPerson(person[i]);
                }
            }
        },

        sendRegistration: function (registration) {
            console.log(registration);
            return $http.post('saveRegistration', registration)
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

        sendRegistrations: function (registrations) {
            console.log(registrations);
            return $http.post('saveRegistrations', registrations)
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

        checkParticipantStatus: function (courseID) {
            return $http.get('checkParticipantStatus', {params: {courseID: courseID}})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while checkingParticipantStatus');
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
                    });
        },

        getPersons: function () {
            return persons;
        },

        deleteRegistration: function (reg) { // Sletter person.
            for (var i = 0; i < registrations.length; i++) {
                console.log(registrations[i]);
                if (registrations[i].person == reg.person) {
                    if (reg.accomondation !== undefined && reg.accomondation !== undefined){
                        removeRoom(reg.person);
                    }
                    registrations.splice(i, 1);
                }
            }
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
        }
    };

    function addPerson(newPerson) { // Legger til person.
        console.log(newPerson);
        newPerson.personID = generateId();
        var newReg = {};
        newReg.role = newPerson.role;
        newReg.optionalPersonalia = newPerson.opt;
        newReg.person = newPerson;
        registrations.push(newReg);
        $rootScope.$broadcast('personSet', persons);
    };

    function generateId() {
        var highestId = 0;
        for (var i = 0; i < registrations.length; i++) {
            if (registrations[i].person.personID >= highestId) {
                highestId = registrations[i].person.personID;
            }
        }
        return (highestId + 1);
    }

    function personExists(newPerson) { // Sjekker om man oppdaterer eller lager nytt objekt.
        var oldPerson = {};
        oldPerson.exists = false;
        if (typeof(newPerson) !== 'undefined') {
            if (registrations.length > 0) {
                for (var i = 0; i < registrations.length; i++) {
                    if (registrations[i].person.personID == newPerson.personID) {
                        oldPerson.exists = true;
                        oldPerson.index = i;
                    }
                }
            }
        }
        return oldPerson;
    }

    function getPersonName(id) {
        for (var i = 0; i < registrations.length; i++) {
            if (registrations[i].person.personID == id) {
                if (registrations[i].accomondation != null) {
                    console.log(registrations[i]);
                    return registrations[i].person.firstname + " " + registrations[i].person.lastname;
                }

            }
        }
        return null;
    }

    function removeRoom(person) {
        var p = {};
        for (var i = 0; i < registrations.length; i++) {
            if (registrations[i].person.personID == person.personID) {
                p = registrations[i].accomondation.roommateID;
                registrations[i].accomondation = undefined;
                var idx = hasRoom.indexOf(person.personID);
                hasRoom.splice(idx, 1);
            }
        }
        for (var i = 0; i < registrations.length; i++) {
            if (registrations[i].person.personID == p) {
                var idx = hasRoom.indexOf(p);
                hasRoom.splice(idx, 1);
                registrations[i].accomondation = undefined;
            }
        }
        console.log(hasRoom);
        $rootScope.$broadcast('hasRoomSet', hasRoom);
    }

    function personUpdate(person, index) {
        delete person.optionalPersonalia;
        delete person.role;
        console.log(person);
        for (var prop in registrations.person) {
            if (registrations.person[prop] != undefined) {
                registrations.person[index][prop] = person[prop];
            }
        }
    };
}]);