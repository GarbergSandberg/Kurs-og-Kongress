/**
 * Created by eiriksandberg on 05.04.2016.
 */
sessionRegisterApp.controller('AddEventCtrl', ['$scope', '$modal', 'eventService', function ($scope, $modal, eventService) {
    var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerEventModal.html', show: false});
    $scope.showModal = function () {
        myModal.$promise.then(myModal.show);
    };
    $scope.events = eventService.get();
    $scope.update = function (newEvent) {
        console.log("I addEventCtrl, sendes til Service - save() '");
        eventService.save(newEvent);
    };
    $scope.delete = function (newEvent) {
        console.log("I AddEventCtrl - Skal slette eventet. ");
        eventService.delete(newEvent);
    };
}]);