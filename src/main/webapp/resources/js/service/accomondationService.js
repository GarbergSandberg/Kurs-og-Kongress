/**
 * Created by Lars on 03.02.16.
 */
sessionRegisterApp.factory('accomondationService', function() {
    var accomondations = [];
    var accomondationService = {};

    accomondationService.save = function(newAccomondation){
        var old = accomondationExists(newAccomondation);
        console.log(old.exists + " Exists");
        if (old.exists){
            accomondationUpdate(newAccomondation, old.index);
            console.log("Modified object = ");
            for(var property in accomondations[old.index]) {
                console.log(property + "=" + accomondations[old.index][property]); // Test. Remove!!
            }

        } else{
            console.log("I accomondationService.save(), adder nytt accomondation. '");
            accomondationService.add(newAccomondation);
        }
    };

    accomondationService.delete = function (newAccomondation) {
        for (var i = 0; i < accomondations.length; i++) {
            if (accomondations[i].id == newAccomondation.id) {
                accomondations.splice(i, 1);
            }
        }
    };

    accomondationService.add = function(newAccomondation) {
        newAccomondation.id = generateId();
        accomondations.push(newAccomondation);
        console.log("Nytt objekt er lagt til, idnr = " + newAccomondation.id);
    };

    accomondationService.get = function() {
        return accomondations;
    };

    accomondationService.setaccomondations = function(accomondationsSent){
        for (var i = 0; i < accomondationsSent.length; i++){
            var newAccomondation = accomondationsSent[i];
            accomondations.push(newAccomondation);
        }
    };

    function generateId(){
        var highestId = 0;
        for (var i = 0; i < accomondations.length; i++){
            if (accomondations[i].id >= highestId){
                highestId = accomondations[i].id;
            }
        }
        var id = highestId + 1;
        return id;
    }

    function accomondationExists(newAccomondation){
        var oldAccomondation = new Object();
        oldAccomondation.exists = false;
        if (typeof(newAccomondation) !== 'undefined'){
            if(accomondations.length > 0){
                for(var property in newAccomondation) {
                    // console.log(property + "=" + newAccomondation[property]); // Test. Remove!!
                }
                for(var i = 0; i < accomondations.length; i++){
                    if(accomondations[i].id == newAccomondation.id){
                        oldAccomondation.exists = true;
                        oldAccomondation.index = i;
                        console.log(oldAccomondation);
                    }
                }
            }
        }
        return oldAccomondation;
    }

    function accomondationUpdate(accomondation, index){
        for(var prop in accomondation) {
            console.log("property in prop = " + prop);                  // remove console.log after testing is done
            console.log("accomondation[prop] = " + accomondation[prop]);
            if(accomondation[prop] != undefined){
                console.log("accomondationUpdate = " + accomondations[index][prop] + " = " + accomondation[prop]);
                accomondations[index][prop] = accomondation[prop];
            }
        }
    }

    return accomondationService;
});