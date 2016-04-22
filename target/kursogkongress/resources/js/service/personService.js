app.factory('personService', ['$rootScope', function ($rootScope) {
    var persons = [];
    var personService = {};
    var hasRoom = [];

    personService.save = function (registrations) { // Gets an Array of persons, checks if they exist already, if so theyre updated. If not, they are added.
        for (i = 0; i < persons.length; i++) {
            var old = personExistsists(registrations[i].person);
            console.log("Gender : " + registrations[i].person.gender);
            if (old.exists) {
                console.log("Gammel eksisterer.");
                personUpdate(registrations[i].person, old.index);
            } else {
                console.log("Legger til ny.")
                personService.add(registrations[i].person);
            }
        }
    };

    personService.delete = function (newPerson) {
        var idx = persons.indexOf(newPerson);
        persons.splice(idx, 1);
    };

    personService.add = function (newPerson) {
        newPerson.personID = generateId();
        console.log("Ny person: " + newPerson.firstname + ", " + newPerson.gender);
        persons.push(newPerson);
        $rootScope.$broadcast('personSet', persons);
    };

    // FJERNES: (?)
    personService.addRoommate = function (person, mate) { // Dato (ankomst og avreise) skal også inn.
        idx = persons.indexOf(person);
        if (mate == undefined) { // Endre enkeltrom til en verdi istedenfor fornavn og etternavn.. Da MÅ removeRoom også endres.
            personUpdate(person, idx);
        } else {
            idx2 = persons.indexOf(mate);
            accomondation.doubleroom = true;
            var accomondation2 = angular.copy(accomondation);
            accomondation.roommateID = mate.personID;
            console.log("her: " + accomondation.roommateID + " , " + accomondation2.roommateID);
            accomondation2.roommateID = person.personID;
            console.log("her2: " + accomondation.roommateID + " ,  " + accomondation2.roommateID);
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
        $rootScope.$broadcast('hasRoomSet', hasRoom);
    };

    personService.removeRoom = function (person) {
        var idx = hasRoom.indexOf(person);
        var pIdx = persons.indexOf(person);
        hasRoom.splice(idx, 1);
        if (person.personID == person.roommate.personID) {
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
            if (persons[i].personID >= highestId) {
                highestId = persons[i].personID;
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
                    if (persons[i].personID == newPerson.personID) {
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