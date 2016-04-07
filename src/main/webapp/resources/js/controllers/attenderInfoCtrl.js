/**
 * Created by eiriksandberg on 07.04.2016.
 */
attenderInfoApp.controller('attenderInfoCtrl', ['$scope', 'attenderInfoService', function ($scope, attenderInfoService) {
    $scope.test = 'Hei';
    $scope.names = [];
    $scope.$on('names:updated', function(event, data){
        $scope.names = data;
    });

    self.getReg = function(){
        attenderInfoService.getRegistrations(1).then(function(success){
            console.log("Length: " + success.length);
        }, function(error){
        });
    };

    self.getReg();
}]);