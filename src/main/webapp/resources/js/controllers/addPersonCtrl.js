app.controller('AddPersonCtrl', ['$scope', 'personService', 'regService', function ($scope, personService, regService) {
    $scope.persons = personService.get();
    $scope.person = [];
    $scope.tooltip = {title: 'Fjern romkamerat'};

    $scope.days = [{id: 'Mandag'}, {id: 'Tirsdag'}, {id: 'Onsdag'}];
    $scope.selectedDays = [];
    $scope.selectedEvents = [];
    $scope.selectedSessions = [];
    $scope.firstPersonRoom = {};
    $scope.hasRoom = personService.getHasRoom();
    $scope.course;
    $scope.roles = [];
    $scope.dateArray = [];
    $scope.$on('rolesSet', function(event, data){
        console.log("I $scope.on.. setter newRoles.")
        $scope.roles = data;
    });
    $scope.$on('courseSet', function(event, data){
        console.log("Course er satt.. broadcast..");
        $scope.course = data;
    });
    $scope.$on('dateSet', function(event, data){
        $scope.dateArray = data;
    });

    $scope.checkboxAccModel = {
        c1: false,
        rad: false,
        mark: false,
        another: false
    };

    $scope.update = function (newPerson) {
        personService.save(newPerson);
    };

    $scope.repeat = function (number) {
        var times = [];
        for (var i = 0; i < number; i++) {
            times.push(i)
        }
        return times;
    };

    $scope.removePerson = function(person){
        for (i = 0; i<$scope.persons.length; i++) {
            if ($scope.persons[i] === person) {
                personService.delete(person);
            }
        }
    };

    $scope.removeRoom = function(person){
        personService.removeRoom(person);
    };

    $scope.showSaveButton = function(n){
        console.log("Skjekker om n > 0   : " + n);
        (n>0) ? true : false;
    };

    $scope.hasRoommate = function(person){
        for (i = 0; i<$scope.persons.length; i++) {
            if ($scope.persons[i] === person) {
                if ($scope.persons[i].roommate == undefined){
                    return false;
                }
            }
        }
        return true;
    };

    $scope.checkIfHasRoom = function(person){
        for (i = 0; i<$scope.hasRoom.length; i++){
            if ($scope.hasRoom[i] == person){
                return false;
            }
        }
        return true;
    };

    $scope.checkIfSelected = function(person){
        return person != $scope.firstPersonRoom;

    };

    $scope.saveRoom = function(first, second){ // Her skal date også inn.
        if ($scope.checkboxAccModel.rad == true){ // Dobbeltrom.
            personService.hasRoom(first,second); // Legger til personene i ny liste, så man vet hvem som er registrert med overnatting.
            personService.addRoommate(first, second);
        } else { // Enkeltrom.
            personService.hasRoom(first);
            personService.addRoommate(first);
        }
    };

    // Endre disse til å returnere true/false, likt som colorEvent() ? (Renere og mindre kode). (Legger til checkbox-value i en array, if -> true.
    $scope.wholeCourse = function wholeCourse() {
        $scope.selectedDays = [];
    };

    $scope.selectDay = function selectDay(day) {  // http://stackoverflow.com/questions/14514461/angularjs-how-to-bind-to-list-of-checkbox-values
        var idx = $scope.selectedDays.indexOf(day);
        $scope.allDaysCheck = false;
        if (idx > -1) {
            $scope.selectedDays.splice(idx, 1);
        } else {
            $scope.selectedDays.push(day);
        }
    };

    $scope.selectedDate = new Date();
    $scope.fromDate = Date.UTC(2016, 1, 10);  // new Date();
    $scope.untilDate = Date.UTC(2016, 1, 15);
    $scope.minDate = Date.UTC(2016, 1, 9);  // new Date();
    $scope.maxDate = Date.UTC(2016, 1, 15);
    $scope.getType = function (key) {
        return Object.prototype.toString.call($scope[key]);
    };

}]);
