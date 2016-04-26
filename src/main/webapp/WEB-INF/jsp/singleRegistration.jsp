<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 20.04.2016
  Time: 14.16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--
Created by IntelliJ IDEA.
User: Lars
Date: 27.01.16
Time: 09.39
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
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
<body>

<div ng-app="RegApp" style="margin-left:3em; margin-right:3em;">

    <div ng-controller="AddRegCtrl">
        <!-- Arbeidsinfo -->
        <div class="page-header">
            <h1>Enkeltpåmelding til {{course.title}}</h1><br>
        </div>
        <h3>Personalia</h3>
        <form>
            <div>
                <label for="firstname">Fornavn: </label>
                <input type="form-control" ng-model="registration.person.firstname" id="firstname"/>
            </div>
            <div>
                <label for="lastname">Etternavn: </label>
                <input type="form-control" ng-model="registration.person.lastname" id="lastname"/>
            </div>
            <div>
                <label for="number">Nummer: </label>
                <input type="form-control" ng-model="registration.person.phonenumber" id="number" type="number"/>
            </div>
            <div>
                <label for="mail">Mail: </label>
                <input type="form-control" ng-model="registration.person.email" id="mail"/>
            </div>
            <div>
                <label for="birthyear">Fødselsår: </label>
                <input type="form-control" ng-model="registration.person.birthYear" id="birthyear" type="number"/>
            </div>
            <div ng-repeat="opt in course.form.optionalPersonalia">
                <label for="opt">{{opt.parameter}}: </label>
                <input type="checkbox" ng-if="!whichType(opt.type)" ng-model="registration.optionalPersonalia[$index]" ng-init="registration.optionalPersonalia[$index]='false'" value="false" id="opt"/>
                <input type="text" ng-if="whichType(opt.type)" ng-model="registration.optionalPersonalia[$index]" ng-init="registration.optionalPersonalia[$index]=''" value="" id="opt2"/><br>
            </div>
            <label ng-repeat="role in course.roles">
                <input type="radio" name="role" ng-model="registration.role" ng-value="role"/> {{role}}
            </label>
            <br>
            <label>
                <input type="radio" name="gender" value="Mann" ng-model="registration.person.gender"> Mann
                <input type="radio" name="gender" value="Kvinne" ng-model="registration.person.gender"> Kvinne
            </label>
            <br>
            <!--Ekstra (utvides om "checked") -->

        </form>
        <hr/>
        <h3>Arbeidsgiverinfo</h3>
        <div>
            <label for="name">Arbeidsplass: </label>
            <input type="form-control" ng-model="registration.workplace.companyName" id="name"/><br>
        </div>
        <div>
            <label for="adress">Adresse: </label>
            <input type="form-control" ng-model="registration.workplace.address" id="adress"/><br>
        </div>
        <div>
            <label for="postalcode">Postnr: </label>
            <input type="form-control" ng-model="registration.workplace.postalcode" id="postalcode" type="number"/><br>
        </div>
        <div>
            <label for="location">Sted: </label>
            <input type="form-control" ng-model="registration.workplace.location" id="location"/><br>
        </div>
        <!-- Ekstra (utvides om "checked") -->
        <label for="another">
            <input type="checkbox" id="another" ng-model="checkboxAccModel.another"/>
            Ønsker faktura sendt til annen adresse <br>
        </label>
        <div ng-show="checkboxAccModel.another">
            Alternativ fakturaadresse:
            <input type="form-control" ng-model="registration.alternativeInvoiceAddress" id="facturaAdress" size="40"/><br>
        </div>
        <div ng-repeat="opt in course.form.optionalWorkplace">
            <label for="opt2">{{opt.parameter}}: </label>
            <input type="checkbox" ng-if="!whichType(opt.type)" ng-model="registration.optionalWorkplace[$index]" ng-init="registration.optionalWorkplace[$index]='false'" value="false" id="workopt"/>
            <input type="text" ng-if="whichType(opt.type)" ng-model="registration.optionalWorkplace[$index]" ng-init="registration.optionalWorkplace[$index]=''" value="" id="workopt2"/><br>
        </div>
        <hr/>
        <hr/>
        <h3>Overnatting</h3>
        <label for="accomodation">
            <input type="checkbox" id="accomodation" ng-model="checkboxAccModel.c1"/>
            Ønsker overnatting?</label><br>
        <label ng-if="checkboxAccModel.c1">Velg hotell: </label><br>
        <div ng-if="checkboxAccModel.c1" ng-repeat="newacc in course.hotels">
            <button class="btn btn-md"
                    ng-class="colorAccomondation(newacc) ? 'btn-primary' : 'btn-default'"
                    ng-click="selectAccomondation(newacc)"> <label>{{newacc.name}}</label>
                <h5>Pris dobbeltrom: {{newacc.doubleprice}}</h5><h5>Pris enkeltrom:
                    {{newacc.singleprice}}</h5>
            </button>
        </div>

        <div ng-show="checkboxAccModel.c1">
            <label>
                <input type="radio" name="roomType" ng-model="registration.accomondation.doubleroom" ng-value="true"/> Dobbeltrom
                <input type="radio" name="roomType" ng-model="registration.accomondation.doubleroom" ng-value="false"/> Enkeltrom
            </label>
        </div>
        <br>
        <div ng-show="checkboxAccModel.c1 && registration.accomondation.doubleroom">
            <label>
                <label for="shareWith">Del rom med: </label>
                <input type="form-control" ng-model="registration.accomondation.roommate" id="shareWith"/>
            </label>
        </div>

        <form name="datepickerForm" class="form-inline" role="form" ng-show="checkboxAccModel.c1">
            <!-- http://mgcrea.github.io/angular-strap/#/datepickers -->
            <div class="form-group">
                <label class="control-label"><i class="fa fa-calendar"></i> <i class="fa fa-arrows-h"></i> <i
                        class="fa fa-calendar"></i> Ankomst- og avreisedato </label><br><br>
                <div class="form-group col-xs-6">
                    <input type="text" class="form-control" ng-model="registration.accomondation.fromDate"
                           data-min-date="{{course.startDate}}"
                           data-max-date="{{course.endDate}}" placeholder="From" bs-datepicker>
                </div>
                <div class="form-group col-xs-6">
                    <input type="text" class="form-control" ng-model="registration.accomondation.toDate" data-max-date="{{course.endDate}}"
                           data-min-date="{{course.startDate}}" placeholder="Until" bs-datepicker>
                </div>
            </div>
        </form>
        <br>


        <hr/>

        <h3>Påmelding faglig program</h3>
        <label>
            <input type="checkbox" name="allDays" ng-model="allDaysCheck" value="false" ng-click="wholeCourse(allDaysCheck)" ng-checked="allDaysCheck"> Hele
            kurset
        </label> <br>
        <label ng-repeat="date in dateArray">
            <input type="checkbox" name="selectedDays[]" value="{{date}}" ng-checked="selectedDays.indexOf(date) > -1"
                   ng-click="selectDay(date)"> {{date | date:'EEEE'}}
        </label>
        <!-- Sesjoner  http://plnkr.co/edit/jKmxJwDnkuxpgy7zYyx6?p=preview [08.02.2016] -->
        <h3>Påmelding sesjoner</h3>
        <table class="table session">
            <tr ng-repeat="date in dateArray">
                <td align="center" class="session">
                    {{date | date:'EEEE'}} <p>{{date | date:'dd-MM-yyyy'}}<br>
                </td>
                <td ng-repeat="session in course.sessions" ng-if="sameDate(date, session.startTime)">
                    <button ng-class="colorSession(session) ? 'btn btn-primary btn-block': 'btn btn-default btn-block'"
                            ng-click="selectSession(session)"> {{session.title}} <h5>({{session.startTime |
                        date:'HH:mm'}} - {{session.endTime | date:'HH:mm'}})</h5>
                    </button>
                </td>
            </tr>
        </table>
        <hr/>
        <h3>Påmelding arrangementer</h3>
        <table class="table event">
            <tr ng-repeat="date in dateArray">
                <td align="center" class="session">
                    {{date | date:'EEEE'}} <p>{{date | date:'dd-MM-yyyy'}}<br>
                </td>
                <td ng-repeat="event in course.events" ng-if="sameDate(date, event.date)"> <!--  -->
                    <button class="btn btn-lg" name="selectedEvents[]" value="{{event}}"
                            ng-checked="selectedEvent.indexOf(event) > -1"
                            ng-click="selectEvent(event)"
                            ng-class="colorEvent(event) ? 'btn btn-primary btn-block': 'btn btn-default btn-block'">
                        {{event.title}}
                    </button>
                </td>
            </tr>
        </table>
        <br>
        <button style="margin-left:2em;" type="button" class="btn btn-primary"
                ng-click="saveSingleRegistration(registration)">
            Send påmelding
        </button>
    </div>
</div>

</body>
</html>

</body>
</html>
