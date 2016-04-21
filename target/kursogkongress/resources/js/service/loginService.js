

loginApp.factory('loginService', function($http, $location, $window){
    return{
        login:function(user){
            console.log(user);
            return $http.get('loginUser', {params: {username: user.username, password: user.password}})
                .then(
                    function (success) {
                            $window.location.href = "/kursogkongress/courseOverview";
                    },
                    function (error) {
                        console.error('Error while login');
                    }
                );
        },

        addNewUser:function(user){
            console.log(user);
            return $http.post('addNewUser', user)
                .then(
                    function (success) {
                        console.log("Success!");
                    },
                    function (error) {
                        console.error('Error while creating user');
                    }
                );
        }
    }
});