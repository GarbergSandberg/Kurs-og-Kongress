sessionRegisterApp.controller('AddAccomondationCtrl', ['$scope', '$modal', 'accomondationService', function ($scope, $modal, accomondationService) {
    /*var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerAccomondationModal.html', show: false});
    $scope.showModal = function () {
        myModal.$promise.then(myModal.show);
    }; */
    $scope.accomondations = accomondationService.get();
    $scope.update = function (newAccomondation) {
        accomondationService.save(newAccomondation);
    };
    $scope.delete = function (newAccomondation) {
        accomondationService.delete(newAccomondation);
    };
}]);