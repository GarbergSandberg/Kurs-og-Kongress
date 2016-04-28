<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 08.04.2016
  Time: 10.10
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
<div ng-app="registerApp" style="margin-left:3em; margin-right:3em;">
    <div ng-controller="attenderInfoCtrl">
        <table class="table">
            <tr style="background-color:gray;color:black;">
                <td>
                    Kurs
                </td>
                <td>
                    {{selectedParticipant.course.title}}
                </td>
            </tr>
            <tr style="background-color:gray;color:black;">
                <td>
                    Personinfo
                </td>
                <td></td>
            </tr>
            <tr style="color: #ff2c27" ng-if="selectedParticipant.speaker">
                <td>
                    Personen er foredragsholder
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td>
                    Navn
                </td>
                <td>
                    {{selectedParticipant.person.firstname}} {{selectedParticipant.person.lastname}}
                </td>
            </tr>
            <tr>
                <td>
                    Fødselsår
                </td>
                <td>
                    {{selectedParticipant.person.birthYear}}
                </td>
            </tr>
            <tr>
                <td>
                    Rolle
                </td>
                <td>
                    {{selectedParticipant.person.role}}
                </td>
            </tr>
            <tr style="color: #ff2c27" ng-if="selectedParticipant.alternativeInvoiceAddress">
                <td>
                    Faktureringsadresse
                </td>
                <td>
                    {{selectedParticipant.alternativeInvoiceAddress}}
                </td>
            </tr>
            <tr style="background-color:gray;color:black;">
                <td>
                    Arbeidsgiverinfo
                </td>
                <td></td>
            </tr>
            <tr>
                <td>
                    Bedriftsnavn
                </td>
                <td>
                    {{selectedParticipant.workplace.companyName}}
                </td>
            </tr>
            <tr>
                <td>
                    Adresse
                </td>
                <td>
                    {{selectedParticipant.workplace.address}}, {{selectedParticipant.workplace.postalcode}} {{selectedParticipant.workplace.location}}
                </td>
            </tr>
            <tr ng-repeat="workplace in selectedParticipant.course.form.optionalWorkplace">
                <td>
                    {{workplace.parameter}}
                </td>
                <td>
                    {{selectedParticipant.optionalWorkplace[$index].parameter}}
                </td>
            </tr>
            <tr style="background-color:gray;color:black;">
                <td>
                    Sesjoner deltakeren er påmeldt
                </td>
                <td></td>
            </tr>
            <tr ng-repeat="session in selectedParticipant.attendingSessions">
                <td>
                    {{session.title}}
                </td>
                <td>
                    {{session.date | date:'dd/MM/yyyy'}} ({{session.startTime | date:'HH:mm'}} - {{session.endTime | date:'HH:mm'}})
                </td>
            </tr>
            <tr style="background-color:gray; color:black;">
                <td>
                    Arrangementer deltakeren er påmeldt
                </td>
                <td></td>
            </tr>
            <tr ng-if="selectedParticipant.attendingEvents == null">
                Ingen påmeldt på noen arrangementer.
            </tr>
            <tr ng-if="selectedParticipant.attendingEvents !== null" ng-repeat="event in selectedParticipant.attendingEvents">
                <td>
                    {{event.title}}
                </td>
                <td>
                    {{event.date | date:'dd/MM/yyyy'}} ({{event.startTime | date:'HH:mm'}})
                </td>
            </tr>
            <tr style="background-color:gray;color:black;">
                <td>
                    Deltakeren er meldt på følgende dager
                </td>
                <td></td>
            </tr>
            <tr>
                <td>
                    Deltaker skal være med hele kurset
                </td>
                <td>
                    <span ng-show="selectedParticipant.attendingFullCourse">Ja</span> <span ng-hide="selectedParticipant.attendingFullCourse">Nei</span>
                </td>
            </tr>
            <tr ng-repeat="date in selectedParticipant.dates">
                <td>
                </td>
                <td>
                    {{date | date:'dd/MM/yyyy'}}
                </td>
            </tr>
        </table>
        <button type="button" class="btn btn-default" ng-click="showStatistics()">Kursoversikt</button>
    </div>
</div>
</body>
</html>
