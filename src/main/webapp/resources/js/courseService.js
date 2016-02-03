/**
 * Created by eiriksandberg on 03.02.2016.
 */

myApp.factory('courseService', ['$http', '$q', function($http, $q) {
    return {
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

