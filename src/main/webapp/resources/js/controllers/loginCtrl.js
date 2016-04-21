loginApp.controller('loginCtrl', function($scope, loginService){
    $scope.user ={};
    $scope.user.username = "";
    $scope.user.password = "";
    $scope.user.confirmPassword = "";
    $scope.user.admin = false;
    $scope.alert = {
        title: "Oops!",
        content: "Passord stemmer ikke",
        type: "info"
        };
    $scope.showAlert = false;
    $scope.login = function(user){
        loginService.login(user).then(function(success){
            //$scope.msgtxt = success.username + " " + success.password;        Må gjøre noe her!
        }, function(error){
           // $scope.msgtxt = error;                                            Og her
        });
    };

    self.confirmPassword = function(password){
        if($scope.user.password == password){
            return true;
        } else{
            return false;
        }
    };

    $scope.addNewUser = function(user){
        if ($scope.user.password == $scope.user.confirmPassword){
            loginService.addNewUser(user);
        } else{
            $scope.showAlert = true;
        }
    };
});