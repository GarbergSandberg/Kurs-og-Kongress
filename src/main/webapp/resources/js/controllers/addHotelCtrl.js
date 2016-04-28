sessionRegisterApp.controller('addHotelCtrl', ['$scope', '$modal', 'hotelService', function ($scope, $modal, hotelService) {
    $scope.removeDeleteButton;
    $scope.hotels = hotelService.get();
    $scope.update = function (newHotel) {
        hotelService.save(newHotel);
    };
    $scope.delete = function (newHotel) {
        hotelService.delete(newHotel);
    };
    $scope.editHotel = function(hotel){
        $scope.removeDeleteButton = true;
        $scope.addNewHotel = angular.copy(hotelService.getHotel(hotel.id));
        console.log($scope.addNewHotel);
    };
    $scope.deleteButtonCtrl = function(){
        return $scope.removeDeleteButton;
    };
    $scope.addingNewHotel = function(){
        $scope.removeDeleteButton = false;
        $scope.addNewHotel = {};
        $scope.addNewHotel.id = -1;
    }
}]);