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
<body ng-app="loginApp">
    <div ng-controller="loginCtrl">
        <form data-toggle="validator" role="form">
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
                        <input ng-model="user.confirmPassword" type="password" ng-class="confirmPassword(user.confirmPassword) ? 'form-control form-control-success' : 'form-control form-control-danger'" id="inputPasswordConfirm" placeholder="Bekreft" required>
                    </div>
                </div>
            <button ng-click="addNewUser(user)" class="btn btn-primary">Opprett ny bruker</button>
        </form>
    </div>
</body>
</html>
