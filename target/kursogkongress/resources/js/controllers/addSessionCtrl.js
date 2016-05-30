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
        $scope.newSession = {};
        $scope.removeDeleteButton = false;
        sessionService.date(id);
        $scope.date = id;
    };
    $scope.delete = function (newSession) {
        sessionService.delete(newSession);
    };
    $scope.sessions = sessionService.get();
    $scope.update = function (newSession) {
        sessionService.save(newSession);
    };
    $scope.filterCopySession= function(item) {
        if(item.getDate() == $scope.date.getDate()){
            return false;
        }
        return true;
    };
    $scope.editSession = function(session){
        $scope.removeDeleteButton = true;
        $scope.newSession = angular.copy(sessionService.getSession(session.id));
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