<%--
  Created by IntelliJ IDEA.
  User: Lars
  Date: 12.02.16
  Time: 12.58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="margin-left" uri="http://www.springframework.org/tags/form" %>

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
    <spring:url value="resources/js/app/registrationApp.js" var="appJs"/>
    <spring:url value="resources/js/app/courseOverviewApp.js" var="myApp"/>
    <spring:url value="resources/js/service/eventRegisterService.js" var="appEventService"/>
    <spring:url value="resources/js/service/personService.js" var="personService"/>
    <spring:url value="resources/js/service/regService.js" var="regService"/>
    <spring:url value="resources/js/controllers/addRegCtrl.js" var="regCtrl"/>

    <script src="${appJs}"></script>
    <script src="${myApp}"></script>
    <script src="${appEventService}"></script>
    <script src="${personService}"></script>
    <script src="${regService}"></script>
    <script src="${regCtrl}"></script>
</head>
<html>
<body>
<div ng-app="RegApp" ng-controller="AddRegCtrl"> <!-- http://mgcrea.github.io/angular-strap/#/tabs [16.02.2016]. -->
    <div bs-active-pane="tabs.activeTab" bs-tabs>
        <div ng-repeat="tab in tabs | orderBy:'id'" data-title="{{ tab.title }}" name="{{ tab.title }}" ng-include="tab.content" bs-pane>
        </div>
    </div>
</div>
</body>
</html>
