/**
 * Created by eiriksandberg on 31.03.2016.
 */

loginApp.factory('sessionService', ['$http', function($http){
   return{
       set:function(key, value){
           return sessionStorage.setItem(key,value);
       },
       get:function(key){
           return sessionStorage.getItem(key);
       },
       destroy:function(key){
           return sessionStorage.removeItem(key);
       }
   };
}]);