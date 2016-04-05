/**
 * Created by eiriksandberg on 05.04.2016.
 */
sessionRegisterApp.controller('AddSessionCtrl', ['$scope', '$modal', 'sessionService', function ($scope, $modal, sessionService) {
    var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerSessionModal.html', show: false});
    $scope.dates = sessionService.getDates();
    $scope.repetitiveSession = {};
    $scope.$on('dates:updated', function(event, data){
        $scope.dates = data;
    });
    $scope.passBtnId = function (id) {
        console.log(id);
        sessionService.date(id);
        $scope.date = id;
    };
    $scope.showModal = function () {
        myModal.$promise.then(myModal.show);
    };
    $scope.delete = function (newSession) {
        console.log("I AddSessionCtrl - Skal slette eventet. ");
        sessionService.delete(newSession);
    };

    $scope.sessions = sessionService.get();
    $scope.update = function (newSession) {
        sessionService.save(newSession);
    };
    $scope.filterCopySession= function(item) {
        if(item.id == $scope.date.id){
            return false;
        }
        return true;
    };
}]);