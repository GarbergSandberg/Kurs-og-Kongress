sessionRegisterApp.controller('addHotelCtrl', ['$scope', '$modal', 'hotelService', function ($scope, $modal, hotelService) {
    /*var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerAccomondationModal.html', show: false});
    $scope.showModal = function () {
        myModal.$promise.then(myModal.show);
    }; */
    $scope.hotels = hotelService.get();
    $scope.update = function (newHotel) {
        console.log("Oppdaterer hotell.");
        hotelService.save(newHotel);
    };
    $scope.delete = function (newHotel) {
        console.log("Sletter hotell.");
        hotelService.delete(newHotel);
    };
}]);