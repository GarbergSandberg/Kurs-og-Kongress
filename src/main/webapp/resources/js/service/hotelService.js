/**
 * Created by Lars on 03.02.16.
 */
sessionRegisterApp.factory('hotelService', ['$rootScope', function($rootScope) {
    var hotels = [];
    var hotelService = {};

    hotelService.save = function(newHotel){
        var old = hotelExists(newHotel);
        console.log(old.exists + " Exists");
        if (old.exists){
            hotelUpdate(newHotel, old.index);
            console.log("Modified object = ");
            for(var property in hotels[old.index]) {
                console.log(property + "=" + hotels[old.index][property]); // Test. Remove!!
            }
        } else{
            console.log("I hotelService.save(), adder nytt hotel. '");
            hotelService.add(newHotel);
        }
    };

    hotelService.delete = function (newHotel) {
        for (var i = 0; i < hotels.length; i++) {
            if (hotels[i].id == newHotel.id) {
                hotels.splice(i, 1);
            }
        }
    };

    hotelService.add = function(newHotel) {
        newHotel.id = generateId();
        hotels.push(newHotel);
        $rootScope.$broadcast('setHotels', hotels);
        console.log("Nytt objekt er lagt til, idnr = " + newHotel.id);
    };

    hotelService.get = function() {
        return hotels;
    };

    hotelService.sethotels = function(hotelsSent){
        for (var i = 0; i < hotelsSent.length; i++){
            var newHotel = hotelsSent[i];
            hotels.push(newHotel);
        }
    };

    function generateId(){
        var highestId = 0;
        for (var i = 0; i < hotels.length; i++){
            if (hotels[i].id >= highestId){
                highestId = hotels[i].id;
            }
        }
        var id = highestId + 1;
        return id;
    }

    function hotelExists(newHotel){
        var oldHotel = new Object();
        oldHotel.exists = false;
        if (typeof(newHotel) !== 'undefined'){
            if(hotels.length > 0){
                for(var property in newHotel) {
                    // console.log(property + "=" + newHotel[property]); // Test. Remove!!
                }
                for(var i = 0; i < hotels.length; i++){
                    if(hotels[i].id == newHotel.id){
                        oldHotel.exists = true;
                        oldHotel.index = i;
                        console.log(oldHotel);
                    }
                }
            }
        }
        return oldHotel;
    }

    function hotelUpdate(hotel, index){
        for(var prop in hotel) {
            console.log("property in prop = " + prop);                  // remove console.log after testing is done
            console.log("hotel[prop] = " + hotel[prop]);
            if(hotel[prop] != undefined){
                console.log("hotelUpdate = " + hotels[index][prop] + " = " + hotel[prop]);
                hotels[index][prop] = hotel[prop];
            }
        }
    }
    $rootScope.$broadcast('setHotels', hotels);
    return hotelService;
}]);