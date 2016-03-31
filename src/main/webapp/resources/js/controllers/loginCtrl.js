app.controller('loginCtrl', function($scope, loginService){
    $scope.login = function(user){
        $scope.msgtxt = '';
        loginService.login(user, $scope);
    }
});