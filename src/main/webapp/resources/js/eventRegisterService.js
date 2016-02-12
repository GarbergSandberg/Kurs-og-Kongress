/**
 * Created by Lars on 03.02.16.
 */



myApp.factory('eventService', function() {
    var events = [];
    var eventService = {};

    eventService.save = function(newEvent){
        var old = eventExists(newEvent);
        console.log(old.exists + " Exists");
        if (old.exists){
            eventUpdate(newEvent, old.index);
            console.log("Modified object = ");
            for(var property in events[old.index]) {
                console.log(property + "=" + events[old.index][property]); // Test. Remove!!
            }

        } else{
            console.log("I eventService.save(), adder nytt event. '");
            eventService.add(newEvent);
        }
    };

    eventService.delete = function (newEvent) {
        for (var i = 0; i < events.length; i++) {
            if (events[i].id == newEvent.id) {
                console.log("Har nå funnet eventet som skal bli slettet. Nr: " + i);
                events.splice(i, 1);
                console.log("Objektet er slettet... Håper jeg.. ");
            } else {
                console.log("Objektet finnes ikke. ");
            }
        }
    };

    eventService.add = function(newEvent) {
        newEvent.id = generateId();
        events.push(newEvent);
        console.log("Nytt objekt er lagt til, idnr = " + newEvent.id);
    };

    eventService.get = function() {
        return events;
    };

    eventService.setEvents = function(eventsSent){
        for (var i = 0; i < eventsSent.length; i++){
            var newEvent = eventsSent[i];
            newEvent.date = new Date(eventsSent[i].date);
            newEvent.time = new Date(eventsSent[i].time);
            events.push(newEvent);
        }
    };

    function generateId(){
        var highestId = 0;
        for (var i = 0; i < events.length; i++){
            if (events[i].id >= highestId){
                highestId = events[i].id;
            }
        }
        var id = highestId + 1;
        return id;
    }

    function eventExists(newEvent){
        var oldEvent = new Object();
        oldEvent.exists = false;
        if (typeof(newEvent) !== 'undefined'){
            if(events.length > 0){
                for(var property in newEvent) {
                    // console.log(property + "=" + newEvent[property]); // Test. Remove!!
                }
                for(var i = 0; i < events.length; i++){
                    if(events[i].id == newEvent.id){
                        oldEvent.exists = true;
                        oldEvent.index = i;
                        console.log(oldEvent);
                    }
                }
            }
        }
        return oldEvent;
    }

    function eventUpdate(event, index){
        for(var prop in event) {
            console.log("property in prop = " + prop);                  // remove console.log after testing is done
            console.log("event[prop] = " + event[prop]);
            if(event[prop] != undefined){
                console.log("eventUpdate = " + events[index][prop] + " = " + event[prop]);
                events[index][prop] = event[prop];
            }
        }
    }

    return eventService;
});