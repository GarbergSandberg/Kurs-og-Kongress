app.factory('personService', function () {
    var persons = [{id: '0', firstname: 'Lars', lastname: 'Gar', roommate: null}, {id: '1', firstname: 'Eirik', lastname: 'Sand', roommate: null},
        {id: '2', firstname: 'Marius', lastname: 'Lauv', roommate: null}];
    var personService = {};
    var hasRoom = [];

    personService.save = function (newPerson) { // Gets an Array of persons, checks if they exist already, if so theyre updated. If not, they are added.
        for (i = 0; i < newPerson.length; i++) {
            var old = personExistsists(newPerson[i]);
            (old.exists) ? personUpdate(newPerson[i], old.index) : personService.add(newPerson[i]);
        }
    };

    personService.delete = function (newPerson) {
        var idx = persons.indexOf(newPerson);
        persons.splice(idx, 1);
    };

    personService.add = function (newPerson) {
        newPerson.id = generateId();
        persons.push(newPerson);
    };

    personService.addRoommate = function(person, mate){ // Dato (ankomst og avreise) skal også inn.
        idx = persons.indexOf(person);
        if (mate == undefined){ // Endre enkeltrom til en verdi istedenfor fornavn og etternavn.. Da MÅ removeRoom også endres.
            person.roommate = person;
            personUpdate(person, idx);
        } else {
            idx2 = persons.indexOf(mate);
            person.roommate = mate;
            mate.roommate = person;
            personUpdate(person, idx);
            personUpdate(mate, idx2);
        }
    };

    personService.hasRoom = function(first, second){
        if (second == undefined){
            hasRoom.push(first);
        } else {
            hasRoom.push(first);
            hasRoom.push(second);
        }
    };

    personService.removeRoom = function(person){
        var idx = hasRoom.indexOf(person);
        var pIdx = persons.indexOf(person);
        hasRoom.splice(idx, 1);
        if (person.id == person.roommate.id){
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

    personService.addRoom = function(person){ // Inn med dato.
        personUpdate(person, persons.indexOf(person));
    };

    personService.get = function () {
        return persons;
    };

    personService.getHasRoom = function(){
        return hasRoom;
    };

    function generateId() {
        var highestId = 0;
        for (var i = 0; i < persons.length; i++) {
            if (persons[i].id >= highestId) {
                highestId = persons[i].id;
            }
        }
        return highestId + 1;
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
        for (var prop in person) {
            if (person[prop] != undefined) {
                persons[index][prop] = person[prop];
            }
        }
    }
    return personService;
});