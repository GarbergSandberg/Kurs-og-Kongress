

app.factory('loginService', function($http){
    var mock = {username: 'lars', password: '123'};
    return{
        login:function(user, scope){
            (user.username == mock.username && user.password == mock.password) ? scope.msgtxt='Correct info' : scope.msgtxt='error info';
            //var $promise = $http.post('data/user.java'); // Skal hente userdata fra database osv..
        }
    }
})