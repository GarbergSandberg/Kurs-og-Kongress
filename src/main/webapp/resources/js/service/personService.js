app.factory('personService', ['$rootScope', function ($rootScope) {
    var persons = [{personID: 0, firstname: 'Lars', lastname: 'Gar', birthYear: 1994, phonenumber: 93643247, email: 'la@ga.no', accomondation: null},//, mark: 'Blind', role: 'Hobby', gender: 'male', roommate: null},
        {personID: 1, firstname: 'Eirik', lastname: 'Sand', birthYear: 1994, phonenumber: 93245342, email: 'ei@sa.no', accomondation: null},// mark: 'Døv', role: 'Hobby', gender: 'male', roommate: null},
        {personID: 2, firstname: 'Marius', lastname: 'Lauv', birthYear: 1991, phonenumber: 11111111, email: 'ma@la.no', accomondation: null}];//, mark: 'Psyk', role: 'Profesjonell', gender: 'female', roommate: null}];
    var personService = {};
    var hasRoom = [];

    personService.save = function (newPerson) { // Gets an Array of persons, checks if they exist already, if so theyre updated. If not, they are added.
        for (i = 0; i < newPerson.length; i++) {
            var old = personExistsists(newPerson[i]);
            console.log("Gender : " + newPerson.gender);
            if (old.exists) {
                personUpdate(newPerson[i], old.index);
            } else {
                personService.add(newPerson[i]);
            }
        }
    };

    personService.delete = function (newPerson) {
        var idx = persons.indexOf(newPerson);
        persons.splice(idx, 1);
    };

    personService.add = function (newPerson) {
        newPerson.id = generateId();
        console.log("Ny person: " + newPerson.firstname + ", " + newPerson.gender);
        persons.push(newPerson);
        $rootScope.$broadcast('personSet', persons);
    };

    personService.addRoommate = function (acc, person, mate) { // Dato (ankomst og avreise) skal også inn.
        var accomondation = acc;
        idx = persons.indexOf(person);
        if (mate == undefined) { // Endre enkeltrom til en verdi istedenfor fornavn og etternavn.. Da MÅ removeRoom også endres.
            accomondation.roommateID = person.personID;
            accomondation.doubleroom = false;
            personUpdate(person, idx);
        } else {
            idx2 = persons.indexOf(mate);
            accomondation.doubleroom = true;
            var accomondation2 = accomondation;
            accomondation.roommateID = mate.personID;
            accomondation2.roommateID = person.personID;
            console.log("acc.roommateID = " + mate.personID + ", acc2.roommateID = " + person.personID);
            console.log("BLABLALBA:");
            person.accomondation = accomondation;
            mate.accomondation = accomondation2;
            personUpdate(person, idx);
            personUpdate(mate, idx2);
            console.log(person); // 0
            console.log(mate); // 1
        }
    };

    personService.hasRoom = function (first, second) {
        if (second == undefined) {
            hasRoom.push(first);
        } else {
            hasRoom.push(first);
            hasRoom.push(second);
        }
    };

    personService.removeRoom = function (person) {
        var idx = hasRoom.indexOf(person);
        var pIdx = persons.indexOf(person);
        hasRoom.splice(idx, 1);
        if (person.id == person.roommate.id) {
            person.roommate = null;
            personUpdate(person, pIdx);
        } else { // Slette roommate og roommates roommate.
            var p2 = person.roommate;
            p2.roommate = null;
            person.roommate = null;
            var idx2 = hasRoom.indexOf(p2);
            var pIdx2 = persons.indexOf(p2);
            hasRoom.splice(idx2, 1);
            personUpdate(person, pIdx);
            personUpdate(p2, pIdx2);
        }
    };

    personService.addRoom = function (person) { // Inn med dato.
        personUpdate(person, persons.indexOf(person));
    };

    personService.get = function () {
        return persons;
    };

    personService.getPerson = function (id) {
        for (var i = 0; i<persons.length; i++){
            if (persons[i].personID == id) return persons[i];
        }
    };

    personService.getHasRoom = function () {
        return hasRoom;
    };

    function generateId() {
        var highestId = 0;
        for (var i = 0; i < persons.length; i++) {
            if (persons[i].id >= highestId) {
                highestId = persons[i].id;
            }
        }
        return (highestId + 1);
    }

    function personExistsists(newPerson) { // Sjekker om man oppdaterer eller lager nytt objekt.
        var oldPerson = {};
        oldPerson.exists = false;
        if (typeof(newPerson) !== 'undefined') {
            if (persons.length > 0) {
                for (var i = 0; i < persons.length; i++) {
                    if (persons[i].id == newPerson.id) {
                        oldPerson.exists = true;
                        oldPerson.index = i;
                    }
                }
            }
        }
        return oldPerson;
    }

    function personUpdate(person, index) {
        console.log(person);
        for (var prop in person) {
            if (person[prop] != undefined) {
                persons[index][prop] = person[prop];
            }
        }
    }
    return personService;
}]);