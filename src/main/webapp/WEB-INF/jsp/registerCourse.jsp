<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 18.01.2016
  Time: 11.41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<html>
<head>
    <title>Registrer kurs</title>
</head>
<body>
<div ng-app="registerCourse" ng-controller="formCtrl">
    <form novalidate>
        Tittel på kurs:<br>
        <input type="text" ng-model="user.firstName"><br>
        :<br>
        <input type="text" ng-model="user.lastName">
        <br><br>
        <button ng-click="reset()">RESET</button>
    </form>
    <p>form = {{user}}</p>
    <p>master = {{master}}</p>
</div>

<script>
    var app = angular.module('registerCourse', []);
    app.controller('formCtrl', function($scope) {
        $scope.master = {firstName: "John", lastName: "Doe"};
        $scope.reset = function() {
            $scope.user = angular.copy($scope.master);
        };
        $scope.reset();
    });
</script>
</body>
</html>
