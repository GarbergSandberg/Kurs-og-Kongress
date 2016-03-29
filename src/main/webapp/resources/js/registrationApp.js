var app = angular.module('RegApp', ['ngAnimate', 'ngSanitize', 'mgcrea.ngStrap']);

angular.module('RegApp')

    .config(function ($datepickerProvider) {
        angular.extend($datepickerProvider.defaults, {
            dateFormat: 'dd/MM/yyyy',
            startWeek: 1,
            autoclose: true
        });
    })

    .filter('range', function () { // http://stackoverflow.com/questions/11160513/angularjs-ng-options-create-range [10.02.2016]
        return function (input, min, max) {
            min = parseInt(min);
            max = parseInt(max);
            for (var i = min; i <= max; i++)
                input.push(i);
            return input;
        };
    });

app.controller('MainCtrl', ['$scope', function($scope) {
    $scope.tabs = [
        { id: '1', title:'Enkeltpåmelding', content:'resources/jsp/singleReg.jsp' },
        { id: '2', title:'Gruppepåmelding', content:'resources/jsp/groupReg.jsp'}
    ];
    $scope.tabs.activeTab = 'Gruppepåmelding';
}]);

app.controller('AddPersonCtrl', ['$scope', 'personService', function ($scope, personService) {
    $scope.persons = personService.get();
    $scope.person = [];

    $scope.update = function (newPerson) {
        console.log("Update(person): " + newPerson.firstname);
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
        console.log("removeRoom PersonCtrl..");
        personService.removeRoom(person);
    };
}]);

app.controller('AddRegCtrl', ['$scope', 'personService', function ($scope, personService) {
    //This will hide the DIV by default.
    $scope.roles = ['Sjef', 'Sykepleier', 'Test'];
    $scope.days = [{id: 'Mandag'}, {id: 'Tirsdag'}, {id: 'Onsdag'}];
    $scope.sessions = [{day: 'Mandag', id: 'Sangtime'}, {day: 'Mandag', id: 'Gitarkurs'}, {day: 'Mandag', id: 'Korøvelse'},
        {day: 'Tirsdag', id: 'Sangtime'}, {day: 'Tirsdag', id: 'Gitarkurs'}, {day: 'Tirsdag', id: 'Korøvelse'}];
    $scope.events = [{day: 'Mandag', id: 'Sangtime'}, {day: 'Mandag', id: 'Gitarkurs'}, {day: 'Mandag', id: 'Korøvelse'},
        {day: 'Tirsdag', id: 'Sangtime'}, {day: 'Tirsdag', id: 'Gitarkurs'}, {day: 'Tirsdag', id: 'Korøvelse'}];
    $scope.selectedDays = [];
    $scope.selectedEvents = [];
    $scope.selectedSessions = [];
    $scope.firstPersonRoom = {};
    $scope.persons = personService.get();
    $scope.hasRoom = personService.getHasRoom();

    $scope.checkboxAccModel = {
        c1: false,
        rad: false,
        mark: false,
        another: false
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
            personService.hasRoom(first,second);
            //$scope.hasRoom.push(first); // Legger til personene i ny liste, så man vet hvem som er registrert med overnatting.
            //$scope.hasRoom.push(second);
            personService.addRoommate(first, second);
        } else { // Enkeltrom.
            //$scope.hasRoom.push(first);
            personService.hasRoom(first);
            personService.addRoommate(first);
        }
    };

    $scope.repeat = function (number) {
        var times = [];
        for (var i = 0; i < number; i++) {
            times.push(i)
        }
        return times;
    };


    $scope.colorEvent = function (event) { // Skjekk om id finnes i selectedEvents.
        for (i = 0; i < $scope.selectedEvents.length; i++) {
            if (event == $scope.selectedEvents[i]) {
                return true;
            }
        }
        return false;
    };

    $scope.colorSession = function (session) { // Skjekk om id finnes i selectedEvents.
        for (i = 0; i < $scope.selectedSessions.length; i++) {
            if (session == $scope.selectedSessions[i]) {
                return true;
            }
        }
        return false;
    };

    $scope.selectButton = function (index) { // Opprette en isActive for hver dag?
        $scope.isActive = index;
    };

    $scope.selectSession = function selectSession(session) {
        var idx = $scope.selectedSessions.indexOf(session);
        // Blir unchecked.
        if (idx > -1) {
            $scope.selectedSessions.splice(idx, 1);
        }
        // Blir checked.
        else {
            if (1 > 0) { // Her må man skjekke om tidspunktet (dag og tid) for sesjonen ikke overlapper med en annen sesjon.
                $scope.selectedSessions.push(session);
            }
        }
    };

    $scope.selectEvent = function selectEvent(event) {
        var idx = $scope.selectedEvents.indexOf(event);
        // Blir unchecked.
        if (idx > -1) {
            $scope.selectedEvents.splice(idx, 1);
        }
        // Blir checked.
        else {
            $scope.selectedEvents.push(event);
        }
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


    // Endre disse til å returnere true/false, likt som colorEvent() ? (Renere og mindre kode). (Legger til checkbox-value i en array, if -> true.
    $scope.wholeCourse = function wholeCourse() {
        $scope.selectedDays = [];
    };

    $scope.selectedDate = new Date();
    $scope.fromDate = Date.UTC(2016, 1, 10);  // new Date();
    $scope.untilDate = Date.UTC(2016, 1, 15);
    $scope.minDate = Date.UTC(2016, 1, 9);  // new Date();
    $scope.maxDate = Date.UTC(2016, 1, 15);
    $scope.getType = function (key) {
        return Object.prototype.toString.call($scope[key]);
    };

    $scope.clearDates = function () {
        $scope.selectedDate = null;
    };
}]);

