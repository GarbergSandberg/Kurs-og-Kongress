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
                <input type="form-control" ng-model="nyperson.firstname" id="firstname"/>
            </div>
            <div>
                <label for="lastname">Etternavn: </label>
                <input type="form-control" ng-model="nyperson.lastname" id="lastname"/>
            </div>
            <div>
                <label for="number">Nummer: </label>
                <input type="form-control" ng-model="nyperson.number" id="number"/>
            </div>
            <div>
                <label for="mail">Mail: </label>
                <input type="form-control" ng-model="nyperson.mail" id="mail"/>
            </div>
            <div>
                <label for="birthyear">Fødselsår: </label>
                <input type="form-control" ng-model="nyperson.birthyear" id="birthyear"/>
            </div>
            <div ng-repeat="opt in course.form.optionalPersonalia">
                <label for="opt">{{opt.parameter}}: </label>
                <input type="checkbox" ng-hide="whichType(opt.type)" ng-model="nyperson.opt[$index]" id="opt"/>
                <input type="text" ng-show="whichType(opt.type)" ng-model="nyperson.opt[$index]" id="opt"/><br>
            </div>
            <label ng-repeat="role in course.roles">
                <input type="radio" name="role" ng-model="nyperson.role" ng-value="role"/> {{role}}
            </label>
            <br>
            <label>
                <input type="radio" name="gender" value="Mann" ng-model="nyperson.gender"> Mann
                <input type="radio" name="gender" value="Kvinne" ng-model="nyperson.gender"> Kvinne
            </label>
            <br>
            <!--Ekstra (utvides om "checked") -->

        </form>
        <hr/>
        <h3>Arbeidsgiverinfo</h3>
        <div>
            <label for="name">Arbeidsplass: </label>
            <input type="form-control" ng-model="workplace.name" id="name"/><br>
        </div>
        <div>
            <label for="adress">Adresse: </label>
            <input type="form-control" ng-model="workplace.address" id="adress"/><br>
        </div>
        <div>
            <label for="postalcode">Postnr: </label>
            <input type="form-control" ng-model="workplace.postalcode" id="postalcode"/><br>
        </div>
        <!-- Ekstra (utvides om "checked") -->
        <label for="another">
            <input type="checkbox" id="another" ng-model="checkboxAccModel.another"/>
            Ønsker faktura sendt til annen adresse <br>
        </label>
        <div ng-show="checkboxAccModel.another">
            Alternativ fakturaadresse:
            <input type="form-control" ng-model="workplace.facturaAddress" id="facturaAdress" size="40"/><br>
        </div>
        <div ng-repeat="opt in course.form.optionalWorkplace">
            <label for="opt">{{opt.parameter}}: </label>
            <input type="checkbox" ng-hide="whichType(opt.type)" ng-model="workplace.opt[$index]" id="opt"/>
            <input type="text" ng-show="whichType(opt.type)" ng-model="workplace.opt[$index]" id="opt"/><br>
        </div>
        <hr/>
        <hr/>
        <h3>Overnatting</h3>
        <label for="accomodation">
            <input type="checkbox" id="accomodation" ng-model="checkboxAccModel.c1"/>
            Ønsker overnatting?</label>

        <div ng-show="checkboxAccModel.c1">
            <label>
                <input type="radio" name="roomType" ng-model="checkboxAccModel.rad" ng-value="true"/> Dobbeltrom
                <input type="radio" name="roomType" ng-model="checkboxAccModel.rad" ng-value="false"/> Enkeltrom
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
                    <input type="text" class="form-control" ng-model="course.startDate"
                           data-min-date="{{course.startDate}}"
                           data-max-date="{{course.endDate}}" placeholder="From" bs-datepicker>
                </div>
                <div class="form-group col-xs-6">
                    <input type="text" class="form-control" ng-model="course.endDate" data-max-date="{{course.endDate}}"
                           data-min-date="{{course.startDate}}" placeholder="Until" bs-datepicker>
                </div>
            </div>
        </form>
        <br>


        <hr/>

        <h3>Påmelding faglig program</h3>
        <label>
            <input type="checkbox" name="allDays" ng-model="allDaysCheck" value="allDays" ng-click="wholeCourse()"> Hele
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
                    <button class="btn btn-lg"
                            ng-class="colorSession(session) ? 'btn-primary' : 'btn-default'"
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
                            ng-class="colorEvent(event) ? 'btn-primary' : 'btn-default'">
                        {{event.title}}
                    </button>
                </td>
            </tr>
        </table>
        <br>
        <button style="margin-left:2em;" type="button" class="btn btn-primary"
                ng-click="saveSingleRegistration(nyperson, workplace)">
            Send påmelding
        </button>
    </div>
</div>

</body>
</html>
