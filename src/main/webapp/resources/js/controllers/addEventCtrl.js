/**
 * Created by eiriksandberg on 05.04.2016.
 */
sessionRegisterApp.controller('AddEventCtrl', ['$scope', '$modal', 'eventService', function ($scope, $modal, eventService) {
    /*var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerEventModal.html', show: false});
    $scope.showModal = function () {
        myModal.$promise.then(myModal.show);
    };*/
    $scope.removeDeleteButton;
    $scope.events = eventService.get();
    $scope.update = function (newEvent) {
        eventService.save(newEvent);
    };
    $scope.delete = function (newEvent) {
        eventService.delete(newEvent);
    };
    $scope.editEvent = function(event){
        $scope.removeDeleteButton = true;
        $scope.addNewEvent = angular.copy(eventService.getEvent(event.id));
        console.log($scope.addNewEvent);
    };
    $scope.deleteButtonCtrl = function(){
        return $scope.removeDeleteButton;
    };
    $scope.addingNewEvent = function(){
        $scope.removeDeleteButton = false;
        $scope.addNewEvent = {};
    };
    $scope.validation = function(){
        if(!$scope.addNewEvent.title || !$scope.addNewEvent.maxNumber || !$scope.addNewEvent.price || !$scope.addNewEvent.location || !$scope.addNewEvent.date || !$scope.addNewEvent.time){
            return true;
        } else{
            return false;
        }
    }
}]);