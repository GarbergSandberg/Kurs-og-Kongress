/**
 * Created by Lars on 10.02.16.
 */
app.factory('personService', function () {
    var persons = [];
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
                console.log("Har nå funnet personet som skal bli slettet. Nr: " + i);
                persons.splice(i, 1);
                console.log("Objektet er slettet... Håper jeg.. ");
            } else {
                console.log("Objektet finnes ikke. ");
            }
        }
    };

    personService.add = function (newPerson) { // //
        //console.log("I personService.add(), skal generere id (generateId()). ");
        console.log(newPerson);
        var test = generateId();
        console.log("Testnr = " + test);
        newPerson.id = test;
        console.log("Generert ID, skal legge til ny person i listen NÅ: ");
        persons.push(newPerson);
        console.log("Nytt objekt er lagt til, idnr = " + newPerson.id);
    };

    personService.get = function () {
        return persons;
    };

    function generateId() {
        console.log("i generateId()");
        var highestId = 0;
        for (var i = 0; i < persons.length; i++) {
            if (persons[i].id >= highestId) {
                console.log("Finner høyeste id og lagrer den.. ");
                highestId = persons[i].id;
            }
        }
        var id = highestId + 1;
        console.log("id = høyeste id +1  : " + id);
        return id;
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
                        console.log(oldPerson);
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