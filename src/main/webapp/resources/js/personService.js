/**
 * Created by Lars on 10.02.16.
 */
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
        console.log("Personservice.addRoommate().. ");
        if (mate == undefined){ // Endre enkeltrom til en verdi istedenfor fornavn og etternavn.. Da MÅ removeRoom også endres.
            person.roommate = person;
            personUpdate(person, person.id);
            console.log("Personservice.addRoommate() - first added. - enkeltrom ");
        } else {
            person.roommate = mate; // Endres til person.roommate = mate?  (objekt istedenfor string).
            mate.roommate = person;  // Endres til mate.roommate = person?  (objekt istedenfor string).
            personUpdate(person, person.id);
            personUpdate(mate, mate.id);
            console.log("Personservice.addRoommate() - Dobbeltrom added. ");
        }
    };



    personService.hasRoom = function(first, second){
        console.log("I function hasRoom (Service)");
        if (second == undefined){
            hasRoom.push(first);
        } else {
            console.log("PUSHET: ");
            hasRoom.push(first);
            hasRoom.push(second);
        }
    };

    personService.removeRoom = function(person){
        console.log("I removeRoom (PersonService) ");
        var idx = hasRoom.indexOf(person);
        hasRoom.splice(idx, 1);
        if (person == person.roommate){
            person.roommate = null;
            personUpdate(person, person.id);
        } else { // Slette roommate og roommates roommate.
            var p2 = person.roommate;
            p2.roommate = null;
            person.roommate = null;
            var idx2 = hasRoom.indexOf(p2);
            hasRoom.splice(idx2, 1);
            personUpdate(person, person.id);
            personUpdate(p2, p2.id);

            /*for (i = 0; i<persons.length; i++){
                if (person.roommate == (persons[i].firstname + ' ' + persons[i].lastname)){
                    console.log("Har funnet tilsvarende personer...");
                    persons[i].roommate = null;
                    person.roommate = null;
                    var idx = hasRoom.indexOf(person);
                    var idx2 = hasRoom.indexOf(person.roommate);
                    hasRoom.splice(idx, 1);
                    hasRoom.splice(idx2, 1);
                    personUpdate(person, person.id);
                    personUpdate(persons[i], persons[i].id);
                }
            }*/

        }
    };

    personService.addRoom = function(person){ // Inn med dato.
        personUpdate(person, person.id);
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