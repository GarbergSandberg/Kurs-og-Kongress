/**
 * Created by eiriksandberg on 07.04.2016.
 */
attenderInfoApp.factory('attenderInfoService', function($http, $rootScope){
    var attenderInfoService = {};

    attenderInfoService.getRegistrations = function(courseID){
        console.log(courseID + " = courseID");
        return $http.get('getRegistrations', {params: {course_id: courseID}})
            .then(
                function (success) {
                    setNames(success.data);
                    return success.data;
                },
                function (error) {
                    console.error('Error while retrieving registrations');
                }
            );
    };

    function setNames(success){
        var temp = [];
        for(var i = 0; i < success.length; i++){
            temp.push({firstname: success[i].person.firstname, lastname: success[i].person.lastname});
        }
        $rootScope.$broadcast('names:updated', temp);
    };

    return attenderInfoService;
});