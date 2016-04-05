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
</head>
<body>

<div ng-app="RegApp" ng-controller="AddRegCtrl" style="margin-left:3em; margin-right:3em;">
    <div class="page-header">
        <h1>Enkeltpåmelding</h1><br>
    </div>

    <!-- Radiobuttons (Rolle og kjønn) -->
    <label ng-repeat="role in course.roles">
        <input type="radio" name="role" ng-model="person.role" ng-value="role"/> {{role}}
    </label>
    <br>
    <label>
        <input type="radio" name="gender" ng-model="person.gender" ng-value="male"/> Mann
        <input type="radio" name="gender" ng-model="person.gender" ng-value="female"/> Kvinne
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
        <label for="mark">
            <input type="checkbox" id="mark" ng-model="checkboxAccModel.mark"/>
            Bemerkning/spesiell diett/best.nr/kode etc?
        </label>
        <div ng-show="checkboxAccModel.mark">
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
    <label for="another">
        <input type="checkbox" id="another" ng-model="checkboxAccModel.another"/>
        Ønsker faktura sendt til annen adresse
    </label>
    <div ng-show="checkboxAccModel.another">
        Alternativ fakturaadresse:
        <input type="form-control" ng-model="workplace.facturaAdress" id="facturaAdress" size="40"/>
    </div>
    <hr/>
    <!-- Faglig program -->
    <h3>Påmelding faglig program</h3>
    <label>
        <input type="checkbox" name="allDays" ng-model="allDaysCheck" value="allDays" ng-click="wholeCourse()"> Hele
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
            <td ng-repeat="session in sessions" ng-if="session.day == day.id">
                <button class="btn btn-lg"
                        ng-class="colorSession(session) ? 'btn-primary' : 'btn-default'"
                        ng-click="selectSession(session)"> {{session.id}} <h5>({{session.start | date:'HH:mm'}} - {{session.end | date:'HH:mm'}})</h5>
                </button>
        </tr>
    </table>
    <hr/>

    <h3>Påmelding arrangementer</h3>
    <table class="table event">
        <tr ng-repeat="day in days">
            <td align="center" class="session">
                {{day.id}}<br>
            </td>
            <td ng-repeat="event in events" ng-if="event.day == day.id">
                <button class="btn btn-lg" name="selectedEvents[]" value="{{event}}" ng-checked="selectedEvent.indexOf(event) > -1"
                        ng-click="selectEvent(event)" ng-class="colorEvent(event) ? 'btn-primary' : 'btn-default'"> {{event.id}}
                </button> </td>
        </tr>
    </table>

    <h3>Overnatting</h3>
    <label for="accomodation">
        <input type="checkbox" id="accomodation" ng-model="checkboxAccModel.c1" />
        Ønsker overnatting?</label>

    <div ng-show="checkboxAccModel.c1">
        <label>
            <input type="radio" name="roomType" ng-model="checkboxAccModel.rad" ng-value="true" /> Dobbeltrom
            <input type="radio" name="roomType" ng-model="checkboxAccModel.rad" ng-value="false" /> Enkeltrom
        </label>
    </div>
    <br>
    <div ng-show="checkboxAccModel.c1 && checkboxAccModel.rad">
        <label>
            <label for="place">Del rom med: </label>
            <input type="form-control" ng-model="Accomodation.share" id="shareWith"/>
        </label>
    </div>

    <form name="datepickerForm" class="form-inline" role="form" ng-show="checkboxAccModel.c1">
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
