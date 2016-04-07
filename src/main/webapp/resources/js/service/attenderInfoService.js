/**
 * Created by eiriksandberg on 07.04.2016.
 */
attenderInfoApp.factory('attenderInfoService', function($http){
    return{
        getRegistrations:function(courseID){
            console.log(courseID + " = courseID");
            return $http.get('getRegistrations', {params: {course_id: courseID}})
                .then(
                    function (success) {
                        console.log(success.data);
                    },
                    function (error) {
                        console.error('Error while retrieving registrations');
                    }
                );
        }
    }
});