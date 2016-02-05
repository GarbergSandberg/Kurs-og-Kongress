/**
 * Created by Lars on 04.02.16.
 */

var app = angular.module('RegApp', []);


app.controller('AddRegCtrl', function ($scope) {
    //This will hide the DIV by default.
    $scope.roles = ['Sjef', 'Sykepleier', 'Test'];
    $scope.days = [{id: 'Mandag'}, {id: 'Tirsdag'}, {id: 'Onsdag'}, {id: 'Torsdag'}, {id: 'Fredag'}];
    $scope.IsExtraVisible = false;
    $scope.IsVisible = false;
    $scope.roomVisible = false;
    $scope.shareVisible = false;

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
        if ($scope.showName == "yes"){
            $scope.shareVisible = $scope.showName;
        }
    };



});

