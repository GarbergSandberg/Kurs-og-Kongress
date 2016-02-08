<%--
  Created by IntelliJ IDEA.
  User: Lars
  Date: 27.01.16
  Time: 09.39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <!--This is important-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <spring:url value="resources/js/registrationApp.js" var="appJs"/>
    <spring:url value="resources/js/eventRegisterService.js" var="appEventService"/>
    <script src="${appJs}"></script>
    <script src="${appEventService}"></script>
</head>
<body>

<div ng-app="RegApp" ng-controller="AddRegCtrl" style="margin-left:3em; margin-right:3em;">
    <div class="page-header">
        <h1>Påmelding til kurset "Tittel"</h1><br>
    </div>

    <!-- Radiobuttons (Rolle og kjønn) -->
    <label ng-repeat="role in roles">
        <input type="radio" name="role" ng-model="person.role" ng-value="role"/> {{role}}
    </label>
    <br>
    <label>
        <input type="radio" name="gender" ng-model="person.genfer" ng-value="male"/> Mann
        <input type="radio" name="gender" ng-model="person.genfer" ng-value="female"/> Kvinne
    </label>
    <hr/>

    <!-- Personalia -->
    <h3>Personalia</h3>
    <div class="personalia">
        <div>
            <label for="firstname">Fornavn: </label>
            <input type="form-control" ng-model="person.firstname" id="firstname"/>
        </div>
        <div>
            <label for="lastname">Etternavn: </label>
            <input type="form-control" ng-model="person.lastname" id="lastname"/>
        </div>
        <div>
            <label for="birthyear">Fødselsår: </label>
            <input type="form-control" ng-model="person.birtyear" id="birthyear"/>
        </div>
        <div>
            <label for="title">Tittel: </label>
            <input type="form-control" ng-model="person.title" id="title"/>
        </div>
        <div>
            <label for="number">Nummer: </label>
            <input type="form-control" ng-model="person.number" id="number"/>
        </div>
        <div>
            <label for="mail">Mail: </label>
            <input type="form-control" ng-model="person.mail" id="mail"/>
        </div>

        <!-- Ekstra (utvides om "checked") -->
        <label for="extraMark">
            <input type="checkbox" id="extraMark" ng-model="ShowMarkExtra" ng-change="ShowHideExtra()"/>
            Bemerkning/spesiell diett/best.nr/kode etc?
        </label>
        <div ng-show="IsExtraVisible">
            Beskrivelse:
            <textarea ng-model="person.description" class="form-control" id="description" rows="3"></textarea>
        </div>
        <hr/>
    </div>

    <!-- Arbeidsinfo -->
    <h3>Arbeidsgiverinfo</h3>
    <div>
        <label for="name">Arbeidsplass: </label>
        <input type="form-control" ng-model="workplace.name" id="name"/>
    </div>
    <div>
        <label for="adress">Adresse: </label>
        <input type="form-control" ng-model="workplace.adress" id="adress"/>
    </div>
    <div>
        <label for="postalcode">Postnr: </label>
        <input type="form-control" ng-model="workplace.postalcode" id="postalcode"/>
    </div>
    <div>
        <label for="place">Sted: </label>
        <input type="form-control" ng-model="workplace.place" id="place"/>
    </div>
    <!-- Ekstra (utvides om "checked") -->
    <label for="mark">
        <input type="checkbox" id="mark" ng-model="ShowMark" ng-change="ShowHide()"/>
        Ønsker faktura sendt til annen adresse
    </label>
    <div ng-show="IsVisible">
        Alternativ fakturaadresse:
        <input type="form-control" ng-model="workplace.facturaAdress" id="facturaAdress" size="40"/>
    </div>
    <hr/>
    <!-- Faglig program -->
    <h3>Påmelding faglig program</h3>
    <label>
        <input type="checkbox" name="allDays" ng-model="allDaysCheck" value="allDays" ng-click="isChecked()"> Hele
        kurset
    </label> <br>

    <label ng-repeat="day in days">
        <input type="checkbox" name="selectedDays[]" value="{{day}}" ng-checked="selectedDays.indexOf(day) > -1"
               ng-click="selectDay(day)"> {{day.id}}
    </label>


    <hr/>
    <!-- Sesjoner  http://plnkr.co/edit/jKmxJwDnkuxpgy7zYyx6?p=preview [08.02.2016] -->
    <h3>Påmelding sesjoner</h3>
    <table class="table session">
        <tr ng-repeat="day in days">
            <td align="center" class="session">
                {{day.id}}<br>
            </td>
            <td class="session" ng-repeat="session in sessions" ng-if="session.day == day.id">
                <button type="button" class="btn"
                        ng-class="{true: 'btn-primary', false: 'btn-default'}[isActive == $index]"
                        ng-click="selectButton($index)"> {{session.id}}
                </button>
            </td>
        </tr>

        <!-- Virker, men kan kun velge en knapp.
        <tr ng-repeat="day in days">
            <td align="center" class="session">
                {{day.id}}<br>
            </td>
            <td class="session" ng-repeat="session in sessions" ng-if="session.day == day.id">
                <button type="button" class="btn"
                        ng-class="{true: 'btn-primary', false: 'btn-default'}[isActive == $index]"
                        ng-click="selectButton($index, session.day)"> {{session.id}}
                </button>
            </td>
        </tr> -->
    </table>

    <hr/>

    <h3>Overnatting</h3>
    <label for="accomodation">
        <input type="checkbox" id="accomodation" ng-model="showRoom" ng-change="accomodation()"/>
        Ønsker overnatting?</label>

    <div ng-show="roomVisible">
        <label>
            <input type="radio" name="roomType" ng-model="showName" value="yes" ng-change="roomType()"/> Dobbeltrom
            <input type="radio" name="roomType" ng-model="showName" value="no" ng-change="roomType()"/> Enkeltrom
        </label>
    </div>
    <br>
    <div ng-show="shareVisible">
        <label>
            <label for="place">Del rom med: </label>
            <input type="form-control" ng-model="Accomodation.share" id="shareWith"/>
        </label>
    </div>

    <form name="datepickerForm" class="form-inline" role="form" ng-show="roomVisible">
        <!-- http://mgcrea.github.io/angular-strap/#/datepickers -->
        <div class="form-group">
            <label class="control-label"><i class="fa fa-calendar"></i> <i class="fa fa-arrows-h"></i> <i
                    class="fa fa-calendar"></i> Ankomst- og avreisedato </label><br><br>
            <div class="form-group col-xs-6">
                <input type="text" class="form-control" ng-model="fromDate" data-min-date="{{minDate}}"
                       data-max-date="{{maxDate}}" placeholder="From" bs-datepicker>
            </div>
            <div class="form-group col-xs-6">
                <input type="text" class="form-control" ng-model="untilDate" data-max-date="{{maxDate}}"
                       data-min-date="{{minDate}}" placeholder="Until" bs-datepicker>
            </div>
        </div>
    </form>


    <hr/>
</div>

</body>
</html>
