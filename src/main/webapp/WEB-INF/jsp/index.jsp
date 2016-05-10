<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="//cdn.jsdelivr.net/fontawesome/4.3.0/css/font-awesome.css">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="//mgcrea.github.io/angular-strap/styles/libs.min.css">
    <link rel="stylesheet" href="//mgcrea.github.io/angular-strap/styles/docs.min.css">
    <link rel="stylesheet" href="resources/css/loginPage.css">
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
            <div class="container">
                <div class="row">
                    <div class="col-sm-6 col-md-4 col-md-offset-4">
                        <div class="account-wall">
                            <h2 class="text-center"><small>Kurs- og Kongresservice</small></h2>
                            <form class="form-signin">
                                <input type="text" class="form-control" placeholder="Brukernavn" required autofocus ng-model="user.username">
                                <input type="password" class="form-control" placeholder="Passord" required ng-model="user.password">
                                <button class="btn btn-lg btn-primary btn-block" type="submit" ng-click="login(user)">Logg inn</button>
                                <h4 class="text-center" ng-show="error"><small>Feil brukernavn eller passord</small></h4>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
