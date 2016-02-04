/**
 * Created by Lars on 04.02.16.
 */

var app = angular.module('RegApp', []);


app.controller('AddRegCtrl', function ($scope) {
    //This will hide the DIV by default.
    $scope.IsExtraVisible = false;
    $scope.IsVisible = false;

    $scope.ShowHideExtra = function () {
        $scope.IsExtraVisible = $scope.ShowMarkExtra;
    };
    $scope.ShowHide = function () {
        $scope.IsVisible = $scope.ShowMark;
    };


    $scope.roles = ['Sjef', 'Sykepleier', 'Test'];
    $scope.dayses = ['Mandag', 'Tirsdag', 'Onsdag'];
    $scope.days = [{id: 'Mandag'}, {id: 'Tirsdag'}, {id: 'Onsdag'}, {id: 'Torsdag'}, {id: 'Fredag'}];
});

