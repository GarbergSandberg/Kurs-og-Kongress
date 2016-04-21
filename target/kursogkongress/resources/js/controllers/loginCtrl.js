loginApp.controller('loginCtrl', function($scope, loginService){
    $scope.user ={};
    $scope.user.username = "";
    $scope.user.password = "";
    $scope.user.confirmPassword = "";
    $scope.login = function(user){
        loginService.login(user).then(function(success){
            //$scope.msgtxt = success.username + " " + success.password;        Må gjøre noe her!
        }, function(error){
           // $scope.msgtxt = error;                                            Og her
        });
    };

    $scope.confirmPassword = function(password){
        if($scope.user.password == password){
            return true;
        } else{
            return false;
        }
    };

    $scope.addNewUser = function(user){
        loginService.addNewUser(user)
    };
});