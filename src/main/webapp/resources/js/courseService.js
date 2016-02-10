/**
 * Created by eiriksandberg on 03.02.2016.
 */

myApp.factory('courseService', ['$http', '$q','$rootScope', function($http, $q, $rootScope) {
    var form = {};
    return {
        prepareForm: function(){
            $rootScope.$broadcast('prepareForm');
        },

        setForm: function(newForm){
            form = newForm;
        },

        getForm: function(){
            return form;
        },

        sendInfo: function (course) {
            return $http.post('saveinformation_json', course)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while sendingInfo');
                        return $q.reject(errResponse.data);
                    }
                );
        }
    }
}]);

