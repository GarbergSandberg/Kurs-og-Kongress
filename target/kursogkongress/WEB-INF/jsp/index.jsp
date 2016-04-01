<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <spring:url value="resources/js/courseOverviewApp.js" var="courseOverviewApp"/>
    <spring:url value="resources/js/controllers/loginCtrl.js" var="loginCtrl"/>
    <spring:url value="resources/js/controllers/homeCtrl.js" var="homeCtrl"/>
    <spring:url value="resources/js/service/loginService.js" var="loginService"/>
    <spring:url value="resources/js/service/sessionService.js" var="sessionService"/>
    <script src="${appJs}"></script>
    <script src="${loginCtrl}"></script>
    <script src="${loginService}"></script>
    <script src="${sessionService}"></script>
    <script src="${courseOverviewApp}"></script>
</head>
<body>
<div ng-app="loginApp">
    <div ng-controller="loginCtrl">
        <form role="form" name="form1">
            Welcome {{user.email}}
            <div class="form-group">
                <ul>
                    <li><label for="usermail">Email</label>
                        <input type="text" name="username" placeholder="yourname@email.com" required ng-model="user.username" id="usermail"></li>
                    <li><label for="password">Password</label>
                        <input type="password" name="password" placeholder="password" required ng-model="user.password" id="password"></li>
                    <li>
                        <input class="btn btn-default" type="submit" ng-click="login(user)" value="Login"></li>
                </ul>
            </div>
            {{msgtxt}}
        </form>
    </div>
</div>
</body>
</html>
