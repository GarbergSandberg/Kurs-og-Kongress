/**
 * Created by Lars on 10.02.16.
 */
app.factory('personService', function () {
    var persons = [{id: '1', firstname: 'Lars', roommate: ''}, {id: '2', firstname: 'Eirik', roommate: ''}, {id: '3', firstname: 'Marius', roommate: ''}];
    var personService = {};

    personService.save = function (newPerson) { // Gets an Array of persons, checks if they exist already, if so theyre updated. If not, they are added.
        for (i = 0; i < newPerson.length; i++) {
            var old = personExistsists(newPerson[i]);
            (old.exists) ? personUpdate(newPerson[i], old.index) : personService.add(newPerson[i]);
        }
    };

    personService.delete = function (newPerson) {
        for (var i = 0; i < persons.length; i++) {
            if (persons[i].id == newPerson.id) {
                persons.splice(i, 1);
            } else {
            }
        }
    };

    personService.add = function (newPerson) {
        var test = generateId();
        newPerson.id = test;
        persons.push(newPerson);
    };

    personService.addRoommate = function(person, mate){ // Dato (ankomst og avreise) skal også inn.
        console.log("I addRoommate - dobbeltrom. ");
        person.roommate = mate;
        mate.roommate = person;
        personUpdate(person, person.index);
        personUpdate(mate, mate.index);
        console.log("I addRoommate - gikk bra. " + person);
    };

    personService.addRoom = function(person){ // Inn med dato.
        console.log("I addRoom() - enkeltrom. ");
        personUpdate(person, person.index);
        person.roommate = 'Enkeltrom';
    };

    personService.get = function () {
        return persons;
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
        var oldPerson = new Object();
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