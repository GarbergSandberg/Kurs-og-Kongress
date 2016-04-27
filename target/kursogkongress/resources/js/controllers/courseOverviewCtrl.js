/**
 * Created by eiriksandberg on 05.04.2016.
 */
sessionRegisterApp.controller('OverviewCtrl', ['$scope', 'courseService', '$window', function ($scope, courseService, $window) {
    $scope.selectedMonth = -1;
    $scope.selectedYear = "";
    $scope.courses = {};
    $scope.panels = [];
    $scope.panels.activePanel = -1;
    $scope.$watch("panels.activePanel", function(newValue, oldValue) {
    });
    $scope.years = [];
    $scope.months = ['Januar', 'Februar', 'Mars', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Desember', 'Alle'];
    $scope.dateFilter = {};
    $scope.loading = true;

    $scope.editCourse = function(id){
        self.setSessionID(id);
    };

    $scope.getStatistics = function(id){
        self.setSessionStatistics(id);
    };

    self.setCourse = function(courseRecieved){
        var course = {};
        var c = courseRecieved;
        if (c.title != null){
            course.title = c.title;
        }
        if (c.description != null){
            course.description = c.description;
        }
        if (c.startDate != null){
            course.startDate = new Date(c.startDate);
            //course.filterDate = $scope.fullDateConverter(course.startDate.getFullYear(), course.startDate.getMonth());
        }
        if (c.endDate != null){
            course.endDate = new Date(c.endDate);
            //course.filterDate = $scope.fullDateConverter(course.endDate.getFullYear(), course.endDate.getMonth());
        }
        if (c.id != null){
            course.id = c.id;
        }
        if (c.publicCourse != null){
            course.publicCourse = c.publicCourse;
        }
        return course;
    };

    self.loadApplication = function(){
        courseService.getCourses().then(function(response) {
            $scope.courses = new Array();
            for (var i = 0; i < response.length; i++){
                $scope.courses.push(self.setCourse(response[i]));
                $scope.panels.push({
                    title: $scope.courses[i].title,
                    body: $scope.courses[i].description,
                    startDate: $scope.courses[i].startDate,
                    endDate: $scope.courses[i].endDate,
                    courseID: $scope.courses[i].id})
            }
            $scope.years = self.findYears();
            $scope.years.push("Alle");
            $scope.loading = false;
        }, function(errorResponse){
            console.log("Error in loadApplication()");
        })};

    self.setSessionID = function(id){
        courseService.setSessionStorageID(id).then(function(successCallback){
            $window.location.href = "/kursogkongress/registerCourse";
        }, function(errorCallback){
            console.log("error in setSessionID");
        });
    };

    self.setSessionStatistics = function(id){
        courseService.setSessionStorageID(id).then(function(successCallback){
            $window.location.href = "/kursogkongress/courseStatistics";
        }, function(errorCallback){
            console.log("error in setSessionID");
        });
    };

    $scope.changeColor = function(courseID){
        for(var i = 0; i < $scope.courses.length; i++){
            if ($scope.courses[i].id == courseID){
                if($scope.courses[i].publicCourse){
                    return 'btn btn-success btn-lg';
                } else{
                    return 'btn btn-warning btn-lg'
                }
            }
        }
    };

    $scope.enableRegistration = function(courseID){
        for(var i = 0; i < $scope.courses.length; i++){
            if ($scope.courses[i].id == courseID){
                var newValue = $scope.courses[i].publicCourse;
                newValue = !newValue;
                $scope.courses[i].publicCourse = newValue;
                courseService.enableRegistration(courseID, newValue);
            }
        }
    };

    self.findYears = function(){
        var array = [];
        var currentMin = new Date();
        var currentMax = new Date();
        for (var i = 0; i < $scope.courses.length; i++){
            if ($scope.courses[i].startDate < currentMin){
                currentMin = $scope.courses[i].startDate;
            }
            if($scope.courses[i].endDate > currentMax){
                currentMax = $scope.courses[i].endDate;
            }
        }
        var min = currentMin.getFullYear();
        var max = currentMax.getFullYear();
        if (min == max){
             array.push(min);
            return array;
        } else{
            while(min <= max){
                array.push(min);
                min++;
            }
            return array;
        }
    };

    $scope.selectedMonthFilter = function(element) {
        if($scope.selectedMonth == 0){
            return element.startDate.getMonth() == $scope.selectedMonth;
        }
        if(!$scope.selectedMonth || $scope.selectedMonth == -1 || $scope.selectedMonth == 12) return true;
        return element.startDate.getMonth() == $scope.selectedMonth;
    };

    $scope.selectedYearFilter = function(element) {
        if(!$scope.selectedYear || $scope.selectedYear == "Alle") return true;
        return element.startDate.getFullYear() == $scope.selectedYear;
    };

    self.loadApplication();
}]);