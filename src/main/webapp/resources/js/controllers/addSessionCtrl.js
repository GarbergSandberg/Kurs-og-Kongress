/**
 * Created by eiriksandberg on 05.04.2016.
 */
sessionRegisterApp.controller('AddSessionCtrl', ['$scope', '$modal', 'sessionService', '$filter', function ($scope, $modal, sessionService, $filter) {
    //var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerSessionModal.html', show: false});
    $scope.sessionFilter = $filter('sessionFilter');
    $scope.dates = sessionService.getDates();
    $scope.repetitiveSession = {};
    $scope.removeDeleteButton;
    $scope.$on('dates:updated', function(event, data){
        $scope.dates = data;
    });
    $scope.passBtnId = function (id) {
        $scope.removeDeleteButton = false;
        console.log($scope.removeDeleteButton);
        console.log(id);
        sessionService.date(id);
        $scope.date = id;
    };
    /*$scope.showModal = function () {
        myModal.$promise.then(myModal.show);
    };*/
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
    $scope.showDeleteButton = function(){
        $scope.removeDeleteButton = true;
        console.log($scope.removeDeleteButton);
    };
    $scope.deleteButtonCtrl = function(){
        return $scope.removeDeleteButton;
    };

}]);

sessionRegisterApp.filter("sessionFilter", function(){
    return function(input, outerIndex, dates){
        var array = [];
        for(var i = 0; i < input.length; i++){
            if(input[i].date.getDate() == dates[outerIndex].getDate()){
                if(input[i].date.getMonth() == dates[outerIndex].getMonth()) {
                    array.push(input[i]);
                }
            }
        }
        return array;
    }
});