

loginApp.factory('loginService', function($http, $location, $window){
    return{
        login:function(user){
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
                        return true;
                    },
                    function (error) {
                        return false;
                    }
                );
        },

        deleteUser:function(user){
            return $http.post('deleteUser', user)
                .then(
                    function (success) {
                        console.log("Success!");
                    },
                    function (error) {
                        console.error('Error while creating user');
                    }
                );
        },

        getUsers:function(){
            return $http.get('getUsers')
                .then(
                    function (success) {
                        return success.data;
                    },
                    function (error) {
                        console.error('Error while getting users');
                    }
                );
        },

        getCourses: function(callback){
            return $http.get('getCourses')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while getCourses');
                    }
                );
        },

        addAccess: function(user, course){
            var courseUserResolver = {user: user, course: course};
            return $http.post('addAccess', courseUserResolver)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while adding access');
                    }
                );
        },

        removeAccess: function(user, course){
            var courseUserResolver = {user: user, course: course};
            return $http.post('removeAccess', courseUserResolver)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while removing access');
                    }
                );
        },

        getCourseAccess:function(user){
            return $http.get('getCourseAccess', {params: {username: user.username}})
                .then(
                    function (success) {
                        return success.data;
                    },
                    function (error) {
                        console.error('Error while getting course access to user');
                    }
                );
        },
    }
});