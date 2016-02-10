/**
 * Created by eiriksandberg on 21.01.2016.
 */
var myApp = angular.module('registerApp', ['ngAnimate', 'mgcrea.ngStrap', 'ngRoute']);

myApp.controller('AddSessionCtrl', ['$scope', '$modal', 'sessionService', function ($scope, $modal, sessionService) {
    var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerSessionModal.html', show: false});
    $scope.dates = sessionService.getDates();
    $scope.repetitiveSession = {};
    $scope.$on('dates:updated', function(event, data){
        $scope.dates = data;
    });
    $scope.passBtnId = function (id) {
        console.log(id);
        sessionService.date(id);
        $scope.date = id;
    };
    $scope.showModal = function () {
        myModal.$promise.then(myModal.show);
    };
    $scope.delete = function (newSession) {
        console.log("I AddSessionCtrl - Skal slette eventet. ");
        sessionService.delete(newSession);
    };

    $scope.sessions = sessionService.get();
    $scope.update = function (newSession) {
        sessionService.save(newSession);
    };
    $scope.filterCopySession= function(item) {
        if(item.id == $scope.date.id){
            return false;
        }
        return true;
    };
}]);

myApp.controller('AddEventCtrl', ['$scope', '$modal', 'eventService', function ($scope, $modal, eventService) {
    var myModal = $modal({scope: $scope, templateUrl: '/resources/html/registerEventModal.html', show: false});
    $scope.showModal = function () {
        myModal.$promise.then(myModal.show);
    };
    $scope.events = eventService.get();
    $scope.update = function (newEvent) {
        console.log("I addEventCtrl, sendes til Service - save() '");
        eventService.save(newEvent);
    };
    $scope.delete = function (newEvent) {
        console.log("I AddEventCtrl - Skal slette eventet. ");
        eventService.delete(newEvent);
    };
}]);

myApp.controller('AddCourseCtrl', ['$scope', '$modal', 'sessionService', 'courseService', 'eventService', function ($scope, $modal, sessionService, courseService, eventService) {
    $scope.course = {};
    $scope.roles = [];
    $scope.$watch("course.startDate", function(newValue, oldValue) {
        if ($scope.course.startDate !== undefined && $scope.course.endDate !== undefined) {
            var dates = self.getDates($scope.course.startDate, $scope.course.endDate);
            sessionService.setDates(dates);
        }
    });

    $scope.$watch("course.endDate", function(newValue, oldValue) {
        if ($scope.course.startDate !== undefined && $scope.course.endDate !== undefined) {
            var dates = self.getDates($scope.course.startDate, $scope.course.endDate);
            sessionService.setDates(dates);
        }
    });

    $scope.addRole = function (role) {
        var exists = false;
        for (var i = 0; i < $scope.roles.length; i++) {
            if ($scope.roles[i] == role) {
                exists = true;
            }
        }
        if (!exists) {
            $scope.roles.push(role);
        }
    };

    $scope.removeRole = function (role) {
        for (var i = 0; i < $scope.roles.length; i++) {
            if ($scope.roles[i] == role) {
                $scope.roles.splice(i, 1);
            }
        }
    };

    $scope.save = function (course) {
        course.roles = $scope.roles;
        course.sessions = sessionService.get();
        course.events = eventService.get();
        courseService.prepareForm();
        course.form = courseService.getForm();
        self.sendCourse(course);
    };

    self.sendCourse = function (course) {
        courseService.sendInfo(course).then(function (successCallback) {
            console.log("Course sent" + course);
        }, function (errorCallback) {
            console.log("Error in courseService.sendInfo()");
        })
    };

    Date.prototype.addDays = function(days) {
        var dat = new Date(this.valueOf());
        dat.setDate(dat.getDate() + days);
        return dat;
    };

    self.getDates = function(startDate, stopDate) {
        var dateArray = new Array();
        var currentDate = startDate;
        while (currentDate <= stopDate) {
            dateArray.push({id: currentDate.toString(),
                weekday: self.findWeekday(currentDate.getDay()),
                year: currentDate.getFullYear(),
                month: currentDate.getMonth() + 1,
                day: currentDate.getDate()});
            currentDate = currentDate.addDays(1);
        }
        return dateArray;
    };

    self.findWeekday = function(weekday){
        switch (weekday){
            case 0: return "Søndag";
            case 1: return "Mandag";
            case 2: return "Tirsdag";
            case 3: return "Onsdag";
            case 4: return "Torsdag";
            case 5: return "Fredag";
            case 6: return "Lørdag";
            default: "";
        }
    };
}]);

myApp.controller('RegistrationCtrl', ['$scope', 'courseService', function ($scope, courseService){
    $scope.form = {};
    $scope.form.checkboxModel = {
        hotel : false,
        airplane : false
    };
    $scope.form.requiredPersonalia = [
        {parameter: "Fornavn", type: "Input"},
        {parameter: "Etternavn",type: "Input"},
        {parameter: "Telefonnummer", type: "Input"},
        {parameter: "Epostadresse", type: "Input"},
        {parameter: "Fødselsår", type: "Input"}
    ];
    $scope.form.optionalPersonalia = [{parameter: "Bemerkning", type: "Checkbox"}];
    $scope.form.requiredWorkplace = [
        {parameter: "Arbeidsplass", type: "Input"},
        {parameter: "Adresse", type: "Input"},
        {parameter: "Postnr", type: "Input"},
        {parameter: "Sted", type: "Input"},
        {parameter: "Ønsker faktura sendt til annen adresse", type: "Checkbox"}
    ];
    $scope.form.optionalWorkplace = [];
    $scope.form.extraInfo = [];
    $scope.class = ["btn btn-default", "btn btn-default"];
    $scope.hidden = ["ng-hide", "ng-hide"];
    $scope.classPersonalia = ["btn btn-default", "btn btn-default"];
    $scope.hiddenPersonalia = ["ng-hide", "ng-hide"];
    $scope.classWorkplace = ["btn btn-default", "btn btn-default"];
    $scope.hiddenWorkplace = ["ng-hide", "ng-hide"];
    $scope.$on('prepareForm', function(event) {
        courseService.setForm($scope.form);
    })
    $scope.buttonResolver = function(id){
        switch (id){
            case "inputExtra":
                $scope.class = ["btn btn-primary", "btn btn-default"];
                $scope.hidden = ["ng-show", "ng-hide"];
                break;
            case "checkboxExtra":
                $scope.class = ["btn btn-default", "btn btn-primary"];
                $scope.hidden = ["ng-hide", "ng-show"];
                break;
            case "inputPersonalia":
                $scope.classPersonalia = ["btn btn-primary", "btn btn-default"];
                $scope.hiddenPersonalia = ["ng-show", "ng-hide"];
                break;
            case "checkboxPersonalia":
                $scope.classPersonalia = ["btn btn-default", "btn btn-primary"];
                $scope.hiddenPersonalia = ["ng-hide", "ng-show"];
                break;
            case "inputWorkplace":
                $scope.classWorkplace = ["btn btn-primary", "btn btn-default"];
                $scope.hiddenWorkplace = ["ng-show", "ng-hide"];
                break;
            case "checkboxWorkplace":
                $scope.classWorkplace= ["btn btn-default", "btn btn-primary"];
                $scope.hiddenWorkplace = ["ng-hide", "ng-show"];
                break;
            case "default":
                break;
        };
    };

    $scope.addInput = function(parameter, type, context) {
        var array = self.contextResolver(context);;
        var exists = false;
        for (var i = 0; i < array.length; i++) {
            if (array[i] == parameter) {
                exists = true;
            }
        }
        if (!exists) {
            var resolve = {parameter: parameter, type: type}
            array.push(resolve);
        }
    };

    $scope.removeInput = function (parameter, context) {
        var array = self.contextResolver(context);
        console.log(array);
        for (var i = 0; i < array.length; i++) {
            if (array[i].parameter == parameter) {
                array.splice(i, 1);
            }
        }
    };

    self.contextResolver = function(context) {
        var array = [];
        switch (context){
            case "extraInfo": array = $scope.form.extraInfo; break;
            case "personalia": array = $scope.form.optionalPersonalia; break;
            case "workplace": array = $scope.form.optionalWorkplace; break;
            default: break;
        }
        return array;
    };
}]);

