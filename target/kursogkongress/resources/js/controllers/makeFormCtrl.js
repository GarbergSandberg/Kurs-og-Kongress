/**
 * Created by eiriksandberg on 05.04.2016.
 */
sessionRegisterApp.controller('RegistrationCtrl', ['$scope', 'courseService', function ($scope, courseService){
    $scope.tooltip = {title: 'Her skal det inn info om personalia'};
    $scope.form = {};
    $scope.form.airplane = false;
    $scope.form.optionalPersonalia = [];
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
    });
    $scope.$on('recievedForm', function(event, data){
        if (data.airplane != null) {
            $scope.form.airplane = data.airplane;
        }
        if(data.optionalPersonalia != null){
            $scope.form.optionalPersonalia = data.optionalPersonalia;
        }
        if(data.optionalWorkplace != null){
            $scope.form.optionalWorkplace = data.optionalWorkplace;
        }
        if (data.extraInfo != null){
            $scope.form.extraInfo = data.extraInfo;
        }
    });
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
        }
    };

    $scope.addInput = function(parameter, type, context) {
        var array = self.contextResolver(context);
        var exists = false;
        for (var i = 0; i < array.length; i++) {
            if (array[i] == parameter) {
                exists = true;
            }
        }
        if (!exists) {
            var resolve = {parameter: parameter, type: type};
            console.log(resolve);
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

