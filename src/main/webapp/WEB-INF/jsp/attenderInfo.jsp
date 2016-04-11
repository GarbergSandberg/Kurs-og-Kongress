<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 07.04.2016
  Time: 14.36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="//cdn.jsdelivr.net/fontawesome/4.3.0/css/font-awesome.css">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="//mgcrea.github.io/angular-strap/styles/libs.min.css">
    <link rel="stylesheet" href="//mgcrea.github.io/angular-strap/styles/docs.min.css">
    <link rel="stylesheet" href="resources/css/courseOverview.css">
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-animate.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-sanitize.min.js" data-semver="1.4.5"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.tpl.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/docs/angular-strap.docs.tpl.js" data-semver="v2.3.7"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <spring:url value="resources/js/app/attenderInfoApp.js" var="attender"/>
    <spring:url value="resources/js/controllers/attenderInfoCtrl.js" var="attenderCtrl"/>
    <spring:url value="resources/js/service/attenderInfoService.js" var="attenderService"/>
    <script src="${attender}"></script>
    <script src="${attenderCtrl}"></script>
    <script src="${attenderService}"></script>
</head>
<body>
<div ng-app="attenderInfoApp" style="margin-left:3em; margin-right:3em;">
    <div ng-controller="attenderInfoCtrl">
        <div class="container">
            <div class="jumbotron" id="jumbo" style="margin-bottom: 2em;">
                <h2>Søkefilter</h2>
                <label for="search">Søk etter deltaker:</label>
                <input class="form-control" ng-model="search" id="search">
            </div>
        </div>
        <table class="table">
            <tr ng-repeat="registration in filtered = (registrations | filter:search)">
                <h5 ng-if="filtered == 0" style="text-align: center;">Ingen deltakere</h5>
                <td>
                    {{registration.person.firstname}} {{registration.person.lastname}}
                </td>
                <td style="text-align: right">
                    <button type="button" class="btn btn-default" ng-click="showInvoiceFromList(registration)">Fakturering</button>
                    <button type="button" class="btn btn-default" ng-click="showInfo(registration)">Oversikt</button>
                    Foredragsholder  <input type="checkbox" ng-model="registration.speaker">
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
