loginApp.controller('loginCtrl', function($scope, loginService){
    $scope.login = function(user){
        $scope.msgtxt = '';
        loginService.login(user).then(function(success){
            //$scope.msgtxt = success.username + " " + success.password;        Må gjøre noe her!
        }, function(error){
           // $scope.msgtxt = error;                                            Og her
        });
    };
    $scope.resetSessionStorage = function(){
        sessionStorage.removeItem("cid");
    }
});