/**
 * Created by Lars on 04.02.16.
 */

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

app.controller('AddPersonCtrl', ['$scope', 'personService', function ($scope, personService) {
    $scope.persons = personService.get();
    $scope.person = [];
    $scope.update = function (newPerson) {
        console.log("I addPersonCtrl, sendes til Service - save() '");
        personService.save(newPerson);
    };

    $scope.repeat = function (number) {
        var times = [];
        for (var i = 0; i < number; i++) {
            times.push(i)
        }
        return times;
    };
    /*
     $scope.delete = function (newPerson) {
     console.log("I AddEventCtrl - Skal slette eventet. ");
     personService.delete(newPerson);
     }; */
}]);

app.controller('AddRegCtrl', function ($scope, $http) {
    //This will hide the DIV by default.
    $scope.roles = ['Sjef', 'Sykepleier', 'Test'];
    $scope.days = [{id: 'Mandag'}, {id: 'Tirsdag'}, {id: 'Onsdag'}];
    $scope.sessions = [{day: 'Mandag', id: 'Sangtime'}, {day: 'Mandag', id: 'Gitarkurs'}, {
        day: 'Mandag',
        id: 'Korøvelse'
    },
        {day: 'Tirsdag', id: 'Sangtime'}, {day: 'Tirsdag', id: 'Gitarkurs'}, {day: 'Tirsdag', id: 'Korøvelse'}];

    $scope.events = [{day: 'Mandag', id: 'Sangtime'}, {day: 'Mandag', id: 'Gitarkurs'}, {
        day: 'Mandag',
        id: 'Korøvelse'
    },
        {day: 'Tirsdag', id: 'Sangtime'}, {day: 'Tirsdag', id: 'Gitarkurs'}, {day: 'Tirsdag', id: 'Korøvelse'}];

    $scope.selectedDays = [];
    $scope.selectedEvents = [];
    $scope.IsExtraVisible = false;
    $scope.IsVisible = false;
    $scope.roomVisible = false;
    $scope.shareVisible = false;
    $scope.variables = [];
    $scope.myModel = {range_id: 1};
    $scope.eventtype = "btn btn-primary";

    $scope.color = function (event) { // Skjekk om id finnes i selectedEvents.
        console.log("Kjører checkResolver");
        for (i = 0; i < $scope.selectedEvents.length; i++) {
            if (event == $scope.selectedEvents[i]) {
                return true;
            }
        }
        return false;
    };

    $scope.selectButton = function (index) { // Opprette en isActive for hver dag?
        $scope.isActive = index;
    };

    $scope.selectEvent = function selectEvent(event) {
        var idx = $scope.selectedEvents.indexOf(event);
        // Blir unchecked.
        if (idx > -1) {
            $scope.selectedEvents.splice(idx, 1);
            console.log("Blir fjernet: " + event.id);
        }
        // Blir checked.
        else {
            console.log("Blir checked.");
            $scope.selectedEvents.push(event);
            //$scope.eventtype = "btn btn-primary";
        }
    };

    $scope.selectDay = function selectDay(day) {  // http://stackoverflow.com/questions/14514461/angularjs-how-to-bind-to-list-of-checkbox-values
        var idx = $scope.selectedDays.indexOf(day);
        $scope.allDaysCheck = false;

        // Blir unchecked.
        if (idx > -1) {
            $scope.selectedDays.splice(idx, 1);
        }
        // Blir checked.
        else {
            $scope.selectedDays.push(day);
        }
    };

    $scope.isChecked = function isChecked() {
        $scope.selectedDays = [];
    };

    $scope.ShowHideExtra = function () {
        $scope.IsExtraVisible = $scope.ShowMarkExtra;
    };
    $scope.ShowHide = function () {
        $scope.IsVisible = $scope.ShowMark;
    };

    $scope.accomodation = function () {
        $scope.roomVisible = $scope.showRoom;
    };
    $scope.roomType = function () {
        $scope.shareVisible = false;
        if ($scope.showName == "yes") {
            $scope.shareVisible = $scope.showName;
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

    $scope.clearDates = function () {
        $scope.selectedDate = null;
    };


});

