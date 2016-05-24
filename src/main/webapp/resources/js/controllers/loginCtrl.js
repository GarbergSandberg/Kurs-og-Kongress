loginApp.controller('loginCtrl', ['$scope', 'loginService', '$window', function($scope, loginService, $window){
    $scope.user ={};
    $scope.user.username = "";
    $scope.user.password = "";
    $scope.user.confirmPassword = "";
    $scope.user.admin = false;
    $scope.users = [];
    $scope.courses = [];
    $scope.coursesUserHasAccessTo = [];
    $scope.readyToShow = false;
    $scope.errormessage = "";
    $scope.error = false;

    $scope.courseFilter = function(course){
        for (var i = 0; i < $scope.coursesUserHasAccessTo.length; i++){
            if(course.id == $scope.coursesUserHasAccessTo[i].id){
                return false;
            }
        }
        return true;
    };

    $scope.login = function(user){
        self.login(user);
    };

    self.confirmPassword = function(password){
        if($scope.user.password == password){
            return true;
        } else{
            return false;
        }
    };

    $scope.deleteUser = function(user){
        loginService.deleteUser(user).then(function(success){
            console.log("User deleted");
        }, function(error){
            console.log("Error while deleting user");
        });
    };

    $scope.addNewUser = function(user){
        if ($scope.user.password == $scope.user.confirmPassword){
            loginService.addNewUser(user).then(function(callback){
                if(callback){
                    $window.location.reload();
                } else{
                    $scope.errormessage = "Opprett ny bruker mislyktes. Bruker finnes fra før";
                }
            }, function(error){
                console.log("Error in add new user");
            });
        }
    };

    $scope.addAccess = function(user, course){
        loginService.addAccess(user, course).then(function(response){
            $scope.getCourseAccess(user);
        }, function(error){
            console.log("ERROR setting access");
        });
    };

    $scope.changePassword = function(oldPassword, newPassword){
        console.log(oldPassword, newPassword, sessionStorage.username);
        loginService.changePassword(sessionStorage.username, oldPassword, newPassword).then(function(response){
            $window.location.reload();
        }, function(error){
            console.log("ERROR changing password");
        });
    };

    $scope.removeAccess = function(user, course){
        console.log("Removing access = " + user.username + " course:  " + course.id);
        loginService.removeAccess(user, course).then(function(response){
            $scope.getCourseAccess(user);
        }, function(error){
            console.log("ERROR removing access");
        });
    };

    self.getUsers = function(){
        loginService.getUsers().then(function(success){
            $scope.users = success;
        }, function(error){
            console.log("Error in getUsers() (Ctrl)");
        });
    };

    self.getCourses = function(){
        loginService.getCourses().then(function(response){
            for (var i = 0; i < response.length; i++){
                $scope.courses.push(self.setCourse(response[i]));
            }
        }, function(error){
            console.log("Error getting courses (loginCtrl)");
        })
    };

    self.setCourse = function(courseRecieved){
        var course = {};
        var c = courseRecieved;
        if (c.title != null){
            course.title = c.title;
        }
        if (c.id != null){
            course.id = c.id;
        }
        return course;
    };

    self.login = function(user){
        loginService.login(user).then(function(success){
            sessionStorage.username = user.username;
            console.log(success);
            if(success == null){
                $scope.error = true;
            }
        });
    };

    $scope.getCourseAccess = function(user){
        $scope.readyToShow = false;
        $scope.coursesUserHasAccessTo = [];
        loginService.getCourseAccess(user).then(function(success){
            var temp = success;
            for (var i = 0; i < temp.length; i++){
                for(var u = 0; u < $scope.courses.length; u++){
                    if (temp[i] == $scope.courses[u].id){
                        $scope.coursesUserHasAccessTo.push($scope.courses[u]);
                    }
                }
            }
            $scope.readyToShow = true;
            console.log($scope.coursesUserHasAccessTo);
        }, function (error){
            console.log("Error in getCourseAccess");
        })
    };

    self.getCourses();
    if(sessionStorage.username){
        self.getUsers();
    }
}]);