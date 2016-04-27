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
    <link rel="stylesheet" href="resources/css/sessionRegister.css">
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-animate.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-sanitize.min.js" data-semver="1.4.5"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.tpl.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/docs/angular-strap.docs.tpl.js" data-semver="v2.3.7"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <spring:url value="resources/js/app/sessionRegisterApp.js" var="sessionRegisterApp"/>
    <spring:url value="resources/js/controllers/attenderInfoCtrl.js" var="attenderCtrl"/>
    <spring:url value="resources/js/service/attenderInfoService.js" var="attenderService"/>
    <spring:url value="resources/js/service/statisticsService.js" var="statisticsService"/>
    <spring:url value="resources/js/service/sessionRegisterService.js" var="sessionRegisterService"/>
    <spring:url value="resources/js/service/eventRegisterService.js" var="eventRegisterService"/>
    <script src="${sessionRegisterApp}"></script>
    <script src="${attenderCtrl}"></script>
    <script src="${attenderService}"></script>
    <script src="${statisticsService}"></script>
    <script src="${sessionRegisterService}"></script>
    <script src="${eventRegisterService}"></script>
</head>
<body>
<div ng-app="registerApp" style="margin-left:3em; margin-right:3em;">
    <div ng-controller="attenderInfoCtrl">
        <span ng-if="!change">
            <button type="button" class="btn btn-default" ng-click="showInvoice()">Fakturering</button>
            <button type="button" class="btn btn-default" ng-click="changeRegistration()">Endre</button>
        </span>
        <span ng-if="change">
            <button ng-if="change" type="button" class="btn btn-default" ng-click="cancelChange()">Forkast endringer</button>
            <button ng-if="change" type="button" align="right" class="btn btn-primary" ng-click="updateRegistration(selectedParticipant)">Lagre endringer </button>
        </span>
        <table class="table" ng-if="!change">
            <tr style="background-color:gray;color:black;">
                <td>
                    Kurs
                </td>
                <td>
                    {{selectedParticipant.course.title}}
                </td>
            </tr>
            <tr style="background-color:gray; color:black; padding-top: 15px">
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
            <tr>
                <td>
                    Rolle
                </td>
                <td>
                    {{selectedParticipant.role}}
                </td>
            </tr>
            <tr>
                <td>
                    Bemerkning
                </td>
                <td>
                    {{selectedParticipant.person.mark}}
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
            <tr style="background-color:gray; color:black;">
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
                    {{selectedParticipant.workplace.address}}, {{selectedParticipant.workplace.postalcode}}
                    {{selectedParticipant.workplace.location}}
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
            <tr style="background-color:gray; color:black;">
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
                    {{session.date | date:'dd/MM/yyyy'}} ({{session.startTime | date:'HH:mm'}} - {{session.endTime |
                    date:'HH:mm'}})
                </td>
            </tr>
            <tr style="background-color:gray; color:black;">
                <td>
                    Arrangementer deltakeren er påmeldt
                </td>
                <td></td>
            </tr>
            <tr ng-repeat="event in selectedParticipant.attendingEvents">
                <td>
                    {{event.title}}
                </td>
                <td>
                    {{event.date | date:'dd/MM/yyyy'}} ({{event.startTime | date:'HH:mm'}})
                </td>
            </tr>
            <tr style="background-color:gray; color:black;">
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
                    <span ng-show="selectedParticipant.attendingFullCourse">Ja</span> <span
                        ng-hide="selectedParticipant.attendingFullCourse">Nei</span>
                </td>
            </tr>
            <tr ng-repeat="date in selectedParticipant.dates">
                <td>
                </td>
                <td>
                    {{date | date:'dd/MM/yyyy'}}
                </td>
            </tr>
            <tr  style="background-color:gray; color:black;">
                <td>
                    Overnatting
                </td>
                <td></td>
            </tr>
            <tr ng-if="selectedParticipant.accomondation == null">
                <td>
                    Ingen overnatting
                </td>
            </tr>
            <tr ng-if="selectedParticipant.accomondation !== null">
                <td>Hotell</td>
                <td>
                    {{chosenHotel.name}}
                </td>
            </tr>
            <tr ng-if="selectedParticipant.accomondation !== null">
                <td>
                    Ankomst- og avreisedato
                </td>
                <td>
                    {{selectedParticipant.accomondation.fromDate | date:'dd-MM-yyyy'}} - {{selectedParticipant.accomondation.toDate | date:'dd-MM-yyyy'}}
                </td>
            </tr>
            <tr ng-if="selectedParticipant.accomondation !== null">
                <td>Romtype</td>
                <td ng-if="selectedParticipant.accomondation.doubleroom">
                    Deler dobbeltrom med {{selectedParticipant.accomondation.roommate}}
                </td>
                <td ng-if="!selectedParticipant.accomondation.doubleroom">
                    Enkeltrom
                </td>
            </tr>
        </table>
        <table class="table" ng-if="change">
            <tr style="color: #ff2c27">
                <td>
                    Personen er foredragsholder
                </td>
                <td>
                    <input type="checkbox" name="roomType" ng-model="selectedParticipant.speaker" ng-value="true"/>
                </td>
            </tr>
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
            <tr>
                <td>
                    Navn
                </td>
                <td>
                    <input type="text" ng-model="selectedParticipant.person.firstname">
                    <input type="text" ng-model="selectedParticipant.person.lastname">
                </td>
            </tr>
            <tr>
                <td>
                    Fødselsår
                </td>
                <td>
                    <input type="text" ng-model="selectedParticipant.person.birthYear">
                </td>
            </tr>
            <tr>
                <td>
                    Telefon
                </td>
                <td>
                    <input type="text" ng-model="selectedParticipant.person.phonenumber">
                </td>
            </tr>
            <tr>
                <td>
                    E-post
                </td>
                <td>
                    <input type="text" ng-model="selectedParticipant.person.email">
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
            <tr ng-repeat="personalia in selectedParticipant.course.form.optionalPersonalia">
                <td>
                    {{personalia.parameter}}
                </td>
                <td ng-if="personalia.type == 'Checkbox'">
                    <input type="checkbox" ng-model="selectedParticipant.optionalPersonalia[$index].parameter">
                </td>
                <td ng-if="personalia.type == 'Input'">
                    <input type="text" ng-model="selectedParticipant.optionalPersonalia[$index].parameter">
                </td>
            </tr>
            <tr style="color: #ff2c27">
                <td>
                    Alternativ faktureringsadresse
                </td>
                <td>
                    <input type="text" ng-model="selectedParticipant.alternativeInvoiceAddress">
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
                <td ng-if="change">
                    <input type="text" ng-model="selectedParticipant.workplace.companyName">
                </td>
            </tr>
            <tr>
                <td ng-if="change">
                    Veiadresse
                </td>
                <td ng-if="change">
                    <input type="text" ng-model="selectedParticipant.workplace.address">
                </td>
            </tr>
            <tr ng-if="change">
                <td>
                    Postnummer
                </td>
                <td>
                    <input type="text" ng-model="selectedParticipant.workplace.postalcode">
                </td>
            </tr>
            <tr ng-if="change">
                <td>
                    Sted
                </td>
                <td>
                    <input type="text" ng-model="selectedParticipant.workplace.location">
                </td>
            </tr>
            <tr ng-repeat="workplace in selectedParticipant.course.form.optionalWorkplace">
                <td>
                    {{workplace.parameter}}
                </td>
                <td ng-if="personalia.type == 'Checkbox'">
                    <input type="checkbox" ng-model="selectedParticipant.optionalWorkplace[$index].parameter">
                </td>
                <td ng-if="personalia.type == 'Input'">
                    <input type="text" ng-model="selectedParticipant.optionalWorkplace[$index].parameter">
                </td>
            </tr>
        </table>
        <table class="table session" ng-if="change">
            <tr style="background-color:gray;color:black;">
                <td>
                    Sesjoner deltakeren er påmeldt
                </td>
                <td></td>
            </tr>
        </table>
        <table class="table" ng-if="change">
            <tr ng-repeat="date in dateArray">
                <td align="center" class="session">
                    {{date | date:'EEEE'}} <p>{{date | date:'dd-MM-yyyy'}}
                </td>
                <td class="session" ng-repeat="session in course.sessions" ng-if="sameDate(date, session.startTime)">
                    <button class="btn btn-lg"
                            ng-class="colorSession(session) ? 'btn-primary' : 'btn-default'"
                            ng-click="selectSession(session)"> {{session.title}} ({{session.startTime |
                        date:'HH:mm'}} - {{session.endTime | date:'HH:mm'}})
                    </button>
                </td>
            </tr>
        </table>
        <table class="table session" ng-if="change">
            <tr style="background-color:gray;color:black;">
                <td>
                    Arrangementer deltakeren er påmeldt
                </td>
                <td></td>
            </tr>
        </table>
        <table class="table" ng-if="change">
            <tr ng-repeat="date in dateArray">
                <td align="center" class="session">
                    {{date | date:'EEEE'}} <p>{{date | date:'dd/MM/yyyy'}}
                </td>
                <td class="session" ng-repeat="event in course.events" ng-if="sameDate(date, event.date)"> <!-- -->
                    <button class="btn btn-lg"
                            name="selectedEvents[]" value="{{event}}"
                            ng-click="selectEvent(event)"
                            ng-class="colorEvent(event) ? 'btn-primary' : 'btn-default'">
                        {{event.title}} <h5>Pris: {{event.price}}</h5>
                    </button>
                </td>
            </tr>
            <br ng-if="change">
            <br ng-if="change">
        </table>
        <table class="table" ng-if="change">
            <tr style="background-color:gray;color:black;">
                <td>
                    Deltakeren er meldt på følgende dager
                </td>
                <td></td>
            </tr>
        </table>
        <table class="table" ng-if="change">
            <tr>
                <td>
                    <input type="checkbox" name="selectedParticipant.attendingFullCourse"
                           ng-model="selectedParticipant.attendingFullCourse"
                           value="selectedParticipant.attendingFullCourse" ng-click="wholeCourse()"> Hele kurset
                </td>
                <td ng-repeat="date in dateArray">
                    <input type="checkbox" name="selectedParticipant.dates[]" value="{{date}}"
                           ng-checked="checkDate(date)"
                           ng-click="selectDay(date)"> {{date | date:'EEEE'}} {{date | date:'dd/MM/yyyy'}}
                </td>
            </tr>
            <tr>
        </table>
        <table class="table" ng-if="change">
            </tr>
            <tr style="background-color:gray;color:black;">
                <td>
                    Overnatting
                </td>
                <td>
                    <input type="checkbox" id="accomodation" ng-model="checkboxAccModel.c1" ng-click="makeAccomondation()"/>
                    Ønsker overnatting?</label>
                </td>
            </tr>
            <br ng-if="change">
            <br ng-if="change">
        </table>
        <table class="table" ng-if="change && checkboxAccModel.c1">
            <tr>
                <td>

                </td>
            </tr>
            <tr>
                <td>Hotell</td>
                <td ng-repeat="hotel in course.hotels">
                    <button class="btn btn-md"
                            ng-class="colorHotel(hotel) ? 'btn-primary' : 'btn-default'"
                            ng-click="selectHotel(hotel)"> <label>{{hotel.name}}</label>
                        <h5>Pris dobbeltrom: {{hotel.doubleprice}}  </h5> <h5>Pris enkeltrom:
                            {{hotel.singleprice}} </h5>
                    </button>
                </td>
            </tr>
            <tr>
                <td>Romtype</td>
                <td>
                    <input type="radio" name="roomType" ng-model="selectedParticipant.accomondation.doubleroom" ng-value="true"/> Dobbeltrom
                    <input type="radio" name="roomType" ng-model="selectedParticipant.accomondation.doubleroom" ng-value="false"/> Enkeltrom
                </td>
            </tr>
            <tr ng-if="selectedParticipant.accomondation.doubleroom">
                <td>
                    Del rom med:
                </td>
                <td>
                    <input type="form-control" ng-model="selectedParticipant.accomondation.roommate"/>
                </td>
            </tr>
            <tr>
                <form name="datepickerForm" class="form-inline" role="form">
                    <!-- http://mgcrea.github.io/angular-strap/#/datepickers -->
                    <div class="form-group">
                        <td class="control-label"><i class="fa fa-calendar"></i> <i class="fa fa-arrows-h"></i> <i
                                class="fa fa-calendar"></i> Ankomst- og avreisedato
                        </td>
                        <td>
                            <span class="form-group col-xs-6">
                                <input type="text" class="form-control"
                                       ng-model="selectedParticipant.accomondation.fromDate"
                                       data-min-date="{{course.startDate}}"
                                       data-max-date="{{course.endDate}}" placeholder="From" bs-datepicker>
                            </span>
                            <span class="form-group col-xs-6">
                                <input type="text" class="form-control" ng-model="selectedParticipant.accomondation.toDate"
                                       data-max-date="{{course.endDate}}"
                                       data-min-date="{{course.startDate}}" placeholder="Until" bs-datepicker>
                            </span>
                        </td>
                    </div>
                </form>
            </tr>
        </table>
        <span align="right">
                <button ng-if="!change" type="button" class="btn btn-default" ng-click="showInvoice()">Fakturering</button>
                <button ng-if="!change" type="button" class="btn btn-default" ng-click="changeRegistration()">Endre</button>
                <button ng-if="change" type="button" class="btn btn-default" ng-click="cancelChange()">Forkast endringer</button>
                <button ng-if="change" type="button" align="right" class="btn btn-primary" ng-click="updateRegistration(selectedParticipant)">Lagre
                    endringer
                </button>
            </span>
        </div>
    </div>
</body>
</html>