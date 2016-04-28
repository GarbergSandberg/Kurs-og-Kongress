<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 21.04.2016
  Time: 11.54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="//cdn.jsdelivr.net/fontawesome/4.3.0/css/font-awesome.css">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="//mgcrea.github.io/angular-strap/styles/libs.min.css">
    <link rel="stylesheet" href="//mgcrea.github.io/angular-strap/styles/docs.min.css">
    <link rel="stylesheet" href="resources/css/sessionRegister.css">
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-animate.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-sanitize.min.js" data-semver="1.4.5"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.tpl.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/docs/angular-strap.docs.tpl.js" data-semver="v2.3.7"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <spring:url value="resources/js/app/loginApp.js" var="appJs"/>
    <spring:url value="resources/js/controllers/loginCtrl.js" var="loginCtrl"/>
    <spring:url value="resources/js/service/loginService.js" var="loginService"/>
    <script src="${appJs}"></script>
    <script src="${loginCtrl}"></script>
    <script src="${loginService}"></script>
</head>
<body>
<div  ng-app="loginApp" style="margin-left:3em; margin-right:3em;">
    <div ng-controller="loginCtrl">
        <h2>Legg til ny bruker</h2>
        <form>
            <div class="form-group">
                <label for="inputEmail" class="control-label">Brukernavn</label>
                <input ng-model="user.username" type="text" class="form-control" id="inputEmail" placeholder="Brukernavn" required>
            </div>
            <div class="form-group">
                <label for="inputPassword" class="control-label">Passord</label>
                <div class="form-group">
                    <input ng-model="user.password" type="password" data-minlength="6" class="form-control" id="inputPassword" placeholder="Passord" required>
                </div>
            </div>
            <div class="form-group">
                <label for="inputPasswordConfirm" class="control-label">Bekreft Passord</label>
                <div class="form-group">
                    <input ng-model="user.confirmPassword" type="password" data-minlength="6" class="form-control" id="inputPasswordConfirm" placeholder="Bekreft" required>
                </div>
            </div>
            <div class="checkbox">
                <label><input type="checkbox" ng-model="user.admin">Bruker skal være administrator</label>
            </div>
            <h5 ng-show="errormessage">{{errormessage}}</h5>
            <button ng-disabled="!(user.confirmPassword == user.password) || !user.confirmPassword || !user.password" ng-click="addNewUser(user)" class="btn btn-primary">Opprett ny bruker</button>
        </form>
        <h2>Kurstilgang</h2>
        Velg bruker <select ng-options="user as user.username for user in users" ng-model="selectedUser" ng-change="getCourseAccess(selectedUser)"></select>
        Velg kurs <select ng-options="course as course.title for course in courses | filter:courseFilter" ng-model="selectedCourse"></select>
        <button ng-disabled="!selectedUser || !selectedCourse" ng-click="addAccess(selectedUser, selectedCourse)" class="btn btn-default">Gi tilgang</button>
        <h5>Kurs som personen har tilgang til</h5>
        <table class="table">
            <tr ng-show="coursesUserHasAccessTo == 0 && selectedUser && readyToShow">
                <td>
                    <h5 style="text-align: center;">Bruker har ikke tilgang til noen kurs</h5>
                </td><td></td>
            </tr>
            <tr ng-repeat="course in coursesUserHasAccessTo">
                <td>
                    {{course.title}}
                </td>
                <td style="">
                    <button type="button" id="{{item}}" ng-click="removeAccess(selectedUser, course)"
                            class="close" aria-label="Close">
                        <span aria-hidden="true">&times;</span></button>
                </td>
            </tr>
        </table>

        <h2>Slett bruker</h2>
        Velg person <select ng-options="user as user.username for user in users" ng-model="userToBeDeleted"></select>
        <button ng-disabled="!userToBeDeleted" ng-click="deleteUser(userToBeDeleted)" class="btn btn-danger">Slett bruker</button>
    </div>
</div>
</body>
</html>
