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
        <table class="table">
            <tr style="color: #ff2c27" ng-if="selectedParticipant.speaker">
                <td>
                    <h4>Personen er foredragsholder</h4>
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td>
                    <h4>Kurs</h4>
                </td>
                <td>
                    {{selectedParticipant.course.title}}
                </td>
            </tr>
            <tr>
                <td>
                    <h4>Personinfo</h4>
                </td>
                <td></td>
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
                    Telefon
                </td>
                <td>
                    {{selectedParticipant.person.phonenumber}}
                </td>
            </tr>
            <tr>
                <td>
                    E-post
                </td>
                <td>
                    {{selectedParticipant.person.email}}
                </td>
            </tr>
            <tr ng-repeat="personalia in selectedParticipant.course.form.optionalPersonalia">
                <td>
                    {{personalia.parameter}}
                </td>
                <td>
                    {{selectedParticipant.optionalPersonalia[$index].parameter}}
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
            <tr>
                <td>
                    <h4>Arbeidsgiverinfo</h4>
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
            <tr>
                <td>
                    <h4>Sesjoner deltakeren er påmeldt</h4>
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
            <tr>
                <td>
                    <h4>Deltakeren er meldt på følgende dager</h4>
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
        <button type="button" class="btn btn-default" ng-click="showInvoice()">Fakturering</button>
        <button type="button" class="btn btn-default" ng-click="changeRegistration()">Endre</button>
    </div>
</div>
</body>
</html>