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
    <link rel="stylesheet" href="resources/css/color.css">
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-animate.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-sanitize.min.js" data-semver="1.4.5"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.tpl.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/docs/angular-strap.docs.tpl.js" data-semver="v2.3.7"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.0/angular-messages.min.js"></script>
    <spring:url value="resources/js/app/sessionRegisterApp.js" var="session"/>
    <spring:url value="resources/js/controllers/attenderInfoCtrl.js" var="attenderInfoCtrl"/>
    <spring:url value="resources/js/service/statisticsService.js" var="statisticsService"/>
    <spring:url value="resources/js/service/courseService.js" var="courseService"/>
    <spring:url value="resources/js/service/sessionRegisterService.js" var="sessionRegisterService"/>
    <spring:url value="resources/js/service/eventRegisterService.js" var="eventRegisterService"/>
    <spring:url value="resources/js/service/hotelService.js" var="hotelService"/>
    <spring:url value="resources/js/service/attenderInfoService.js" var="attenderInfoService"/>
    <script src="${session}"></script>
    <script src="${attenderInfoCtrl}"></script>
    <script src="${statisticsService}"></script>
    <script src="${courseService}"></script>
    <script src="${sessionRegisterService}"></script>
    <script src="${eventRegisterService}"></script>
    <script src="${hotelService}"></script>
    <script src="${attenderInfoService}"></script>
</head>
<body>
<div ng-app="registerApp">
    <div ng-controller="attenderInfoCtrl" style="margin-left:7em; margin-right:7em;">
        <table class="table">
            <tr>
                <td>
                    <h3>Kurs & Kongresservice</h3>
                </td>
                <td>
                </td>
            </tr>
            <tr class="tableRowHighlight">
                <td>Personinfo</td>

                <td></td>
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
            <tr class="tableRowHighlight">
                <td>Type utgift</td>

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
            <tr ng-repeat="event in selectedParticipant.attendingEvents">
                <td>
                    {{event.title}}
                </td>
                <td style="text-align: right">
                    {{event.price | number : 2}} kr
                </td>
            </tr>
            <tr style="border-top-style: solid; color:black; font-weight: bold">
                <td>
                    Totalt
                </td>
                <td style="text-align: right">
                    {{selectedParticipant.totalAmount | number : 2}} kr
                </td>
            </tr>
        </table>
        </div>
    </div>
</body>
</html>
