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
    });

app.controller('AddRegCtrl', function ($scope, $http) {
    //This will hide the DIV by default.
    $scope.roles = ['Sjef', 'Sykepleier', 'Test'];
    $scope.days = [{id: 'Mandag'}, {id: 'Tirsdag'}, {id: 'Onsdag'}];
    $scope.sessions = [{day: 'Mandag', id: 'Sangtime'}, {day: 'Mandag', id: 'Gitarkurs'}, {day: 'Mandag', id: 'Korøvelse'},
        {day: 'Tirsdag', id: 'Sangtime'}, {day: 'Tirsdag', id: 'Gitarkurs'}, {day: 'Tirsdag', id: 'Korøvelse'}];
    $scope.selectedDays = [];
    $scope.IsExtraVisible = false;
    $scope.IsVisible = false;
    $scope.roomVisible = false;
    $scope.shareVisible = false;
    $scope.variables = [];

    $scope.selectButton = function(index) { // Opprette en isActive for hver dag?

        $scope.isActive = index;
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

    $scope.isChecked = function isChecked(){
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
    $scope.minDate = Date.UTC(2016, 1, 09);  // new Date();
    $scope.maxDate = Date.UTC(2016, 1, 15);
    $scope.getType = function (key) {
        return Object.prototype.toString.call($scope[key]);
    };

    $scope.clearDates = function () {
        $scope.selectedDate = null;
    };


});

