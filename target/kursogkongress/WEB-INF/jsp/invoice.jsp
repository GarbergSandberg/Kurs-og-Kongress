<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 08.04.2016
  Time: 15.47
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
<div ng-app="attenderInfoApp" style="margin-left:7em; margin-right:7em;">
    <div ng-controller="attenderInfoCtrl">
        <table class="table">
            <tr>
                <td>
                    <h3>Kurs & Kongresservice</h3>
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td>
                    Navn
                </td>
                <td style="text-align: right">
                    {{selectedParticipant.person.firstname}} {{selectedParticipant.person.lastname}}
                </td>
            </tr>
            <tr>
                <td>
                    Adresse
                </td>
                <td style="text-align: right">
                    <span ng-if="selectedParticipant.alternativeInvoiceAddress">{{selectedParticipant.alternativeInvoiceAddress}}</span>
                    <span ng-if="!selectedParticipant.alternativeInvoiceAddress">{{selectedParticipant.workplace.address}}, {{selectedParticipant.workplace.postalcode}} {{selectedParticipant.workplace.location}}</span>
                </td>
            </tr>
            <tr>
                <td style="font-weight: bold">Type utgift</td>

                <td></td>
            </tr>
            <tr ng-repeat="payment in selectedParticipant.cost">
                <td>
                    {{payment.description}}
                </td>
                <td style="text-align: right">
                    {{payment.amount | number : 2}} kr
                </td>
            </tr>
            <tr style="font-weight: bold">
                <td>
                    Totalt
                </td>
                <td style="text-align: right">
                    {{selectedParticipant.totalAmount | number : 2}} kr
                </td>
            </tr>
        </div>
    </div>
</body>
</html>