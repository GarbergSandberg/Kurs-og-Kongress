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
        <div ng-show="!loading">
            <!-- Arbeidsinfo -->
            <div class="page-header">
                <h1>Enkeltpåmelding til {{course.title}}</h1><br>
            </div>


            <div class="form-horizontal" align="center" style="text-align: left; max-width: 90%; min-width: 60%;">
                <h3>Arbeidsgiverinfo</h3>
                <h3>Personalia</h3>
                <div class="form-group">
                    <label for="firstname" class="col-sm-4 control-label">Fornavn: </label>
                    <div class="col-sm-6">
                        <input class="form-control" ng-model="registration.person.firstname" id="firstname"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="lastname" class="col-sm-4 control-label">Etternavn: </label>
                    <div class="col-sm-6">
                        <input class="form-control" ng-model="registration.person.lastname" id="lastname"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="number" class="col-sm-4 control-label">Nummer: </label>
                    <div class="col-sm-6">
                        <input class="form-control" ng-model="registration.person.phonenumber" id="number"
                               type="number"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="mail" class="col-sm-4 control-label">Mail: </label>
                    <div class="col-sm-6">
                        <input class="form-control" ng-model="registration.person.email" id="mail"/>
                    </div>
                </div> <!-- Ekstra (utvides om "checked") -->
                <div class="form-group">
                    <label for="birthyear" class="col-sm-4 control-label">Fødselsår: </label>
                    <div class="col-sm-6">
                        <input class="form-control" ng-model="registration.person.birthYear" id="birthyear"
                               type="number"/>
                    </div>
                </div>


                <div class="form-group" ng-repeat="opt in course.form.optionalPersonalia track by opt.id">
                    <label class="col-sm-4 control-label">{{opt.parameter}} </label>
                    <div class="col-sm-6" ng-hide="whichType(opt.type)">
                        <input type="checkbox" ng-model="registration.optionalPersonalia[$index]"/>
                    </div>
                    <div class="col-sm-6" ng-show="whichType(opt.type)">
                        <input class="form-control" ng-model="registration.optionalPersonalia[$index]"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label"></label>
                    <label ng-repeat="role in course.roles">
                        <input type="radio" name="role" ng-model="registration.role" ng-value="role"/>{{role}}
                        &nbsp
                    </label>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label"></label>
                    <label>
                        <input type="radio" name="gender" value="Mann" ng-model="registration.person.gender"> Mann
                        <input type="radio" name="gender" value="Kvinne" ng-model="registration.person.gender"> Kvinne
                    </label>
                </div>
                <div>
                    <label class="col-sm-4 control-label"></label>
                    <input type="checkbox" ng-model="checkboxMark" ng-change="alert()">
                    <label>Bemerkning/best.nr etc?</label>
                </div>
                <div ng-show="checkboxMark">
                    <label class="col-sm-4 control-label">Beskrivelse</label>
                    <textarea class="col-sm-6" ng-model="registration.person.mark" ng-init="registration.person.mark=''"
                              class="form-control" id="description" rows="2"></textarea>
                </div>
            </div>
        </div>
        <div style="clear: both"></div>
        <hr/>
        <div class="form-horizontal" align="center" style="text-align: left; max-width: 90%; min-width: 60%;">
            <h3>Arbeidsgiverinfo</h3>
            <div class="form-group">
                <label for="name" class="col-sm-4 control-label">Arbeidsplass </label>
                <div class="col-sm-6">
                    <input class="form-control" ng-model="registration.workplace.companyName" id="name"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label" for="address">Adresse </label>
                <div class="col-sm-6">
                    <input class="form-control" ng-model="registration.workplace.address" id="address"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label" for="postalcode">Postnr </label>
                <div class="col-sm-6">
                    <input class="form-control" ng-model="registration.workplace.postalcode" id="postalcode"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label" for="postalcode">Sted </label>
                <div class="col-sm-6">
                    <input class="form-control" ng-model="registration.workplace.location" id="location"/>
                </div>
            </div> <!-- Ekstra (utvides om "checked") -->
            <div class="form-group">
                <label class="col-sm-4 control-label">Ønsker annen fakturaadresse</label>
                <div class="col-sm-6">
                    <input type="checkbox" id="another" ng-model="checkboxAccModel.another"/>
                </div>
            </div>
            <div class="form-group">
                <div ng-show="checkboxAccModel.another">
                    <label class="col-sm-4 control-label">Adresse, postnr og sted</label>
                    <div class="col-sm-6">
                        <input class="form-control" ng-model="registration.alternativeInvoiceAddress"
                               id="alternativeInvoiceAddress" value="" size="40"/>
                    </div>
                </div>
            </div>
            <div class="form-group" ng-repeat="opt in course.form.optionalWorkplace track by opt.id">
                <label class="col-sm-4 control-label">{{opt.parameter}} </label>
                <div class="col-sm-6" ng-hide="whichType(opt.type)">
                    <input type="checkbox" ng-model="registration.optionalWorkplace[$index]"/>
                </div>
                <div class="col-sm-6" ng-show="whichType(opt.type)">
                    <input class="form-control" type="text" ng-model="registration.optionalWorkplace[$index]"/>
                </div>
            </div>
        </div>
        <br>
        <hr/>


        <div align="left" ng-if="course.hotels.length > 0">
            <h3>Overnatting</h3>
        </div>
        <div align="center">
            <div class="form-group">
                <label class="control-label" for="postalcode">Ønsker overnatting </label>
                <input type="checkbox" id="accomodation" ng-model="checkboxAccModel.c1"/>
            </div>
            <label ng-if="checkboxAccModel.c1">Velg hotell: </label><br>
            <div ng-if="checkboxAccModel.c1" ng-repeat="newacc in course.hotels">
                <button class="btn btn-md"
                        ng-class="colorAccomondation(newacc) ? 'btn btn-primary test' : 'btn btn-default test'"
                        ng-click="selectAccomondation(newacc)"><label>{{newacc.name}}</label>
                    <h5>Pris dobbeltrom: {{newacc.doubleprice | number: 2}} kr</h5><h5>Pris enkeltrom:
                        {{newacc.singleprice | number: 2}} kr</h5>
                </button>
                <div ng-if="!(($index+1) % 3)" style="clear: both"></div>
            </div>
            <div style="clear: both"></div>
            <div class="form-group" align="center" ng-if="checkboxAccModel.c1">
                <label>
                    <input type="radio" name="roomType" ng-model="checkboxAccModel.rad" ng-value="true"/>
                    Dobbeltrom &nbsp
                    <input type="radio" name="roomType" ng-model="checkboxAccModel.rad" ng-value="false"/>
                    Enkeltrom
                </label>
            </div>
            <br>
            <div ng-show="checkboxAccModel.c1 && checkboxAccModel.rad">
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
                               data-time-format="dd/mm/yyy"
                               ng-init="course.startDate" placeholder="From" bs-datepicker>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" ng-model="registration.accomondation.toDate"
                               data-time-format="dd/mm/yyy"
                               ng-init="course.endDate" placeholder="Until" bs-datepicker>
                    </div>
                </div>
            </form>
            <br>
        </div>


        <hr/>

        <h3>Påmelding faglig program</h3>
        <h3>
            <small>Priser for kurs</small>
        </h3>
        <table class="table">
            <tr>
                <td><h6>Kursavgift hvis deltaker skal være med hele kurset</h6></td>
                <td><h6>{{course.courseFee}} kr</h6></td>
            </tr>
            <tr>
                <td><h6>Kursavgift per dag hvis deltaker skal være med på deler av kurset</h6></td>
                <td><h6>{{course.courseSingleDayFee}} kr</h6></td>
            </tr>
            <tr>
                <td><h6>Pris for dagpakke</h6></td>
                <td><h6>{{course.dayPackage}} kr</h6></td>
            </tr>
        </table>
        <h3>
            <small>Velg dager</small>
        </h3>
        <hr>

        <div align="center" class="form-group">
            <label>
                <input type="checkbox" name="allDays" ng-model="allDaysCheck" value="allDays"
                       ng-click="wholeCourse(allDaysCheck)" ng-checked="allDaysCheck"> Hele kurset
            </label>
        </div>
        <br>
        <div align="center" class="form-group">
            <span ng-repeat="date in dateArray">
                <input type="checkbox" name="selectedDays[]" value="{{date}}"
                       ng-checked="selectedDays.indexOf(date) > -1"
                       ng-click="selectDay(date)"> {{date | date:'EEEE dd/MM/yyyy'}} &nbsp
            </span>
        </div>
        <!-- Sesjoner  http://plnkr.co/edit/jKmxJwDnkuxpgy7zYyx6?p=preview [08.02.2016] -->
        <h3>Påmelding sesjoner</h3>
        <table class="table session">
            <tr ng-repeat="date in dateArray">
                <td align="center" class="session">
                    {{date | date:'EEEE'}} <p>{{date | date:'dd-MM-yyyy'}}<br>
                </td>
                <td ng-repeat="session in course.sessions" ng-if="sameDate(date, session.startTime)">
                    <button ng-disabled="session.isFull"
                            ng-class="colorSession(session) ? 'btn btn-primary btn-block': 'btn btn-default btn-block'"
                            ng-click="selectSession(session)"> {{session.title}} <h5>({{session.startTime |
                        date:'HH:mm'}} - {{session.endTime | date:'HH:mm'}})</h5>
                    </button>
                </td>
            </tr>
        </table>
        <hr/>
        <h3>Påmelding arrangementer</h3>
        <table class="table session">
            <tr ng-repeat="date in dateArray">
                <td align="center" class="session">
                    {{date | date:'EEEE'}} <p>{{date | date:'dd-MM-yyyy'}}<br>
                </td>
                <td ng-repeat="event in course.events" ng-if="sameDate(date, event.date)"> <!--  -->
                    <button ng-disabled="event.isFull" name="selectedEvents[]" value="{{event}}"
                            ng-checked="selectedEvent.indexOf(event) > -1"
                            ng-click="selectEvent(event)"
                            ng-class="colorEvent(event) ? 'btn btn-primary btn-block': 'btn btn-default btn-block'">
                        {{event.title}} <h5>{{event.price | number: 2}} kr</h5>
                    </button>
                </td>
            </tr>
        </table>
        <br>
        <div align="left" ng-if="course.form.extraInfo.length > 0">
            <h3>Ekstrainfo</h3>
        </div>
        <div align="center" ng-if="course.form.extraInfo.length > 0">
            <div ng-repeat="extraInfo in course.form.extraInfo">
                <br>
                <label>{{extraInfo.parameter}}: </label>
                <input type="checkbox" ng-hide="whichType(extraInfo.type)"
                       ng-init="person[n].extraInfo[$index]='false'"
                       ng-model="person[n].extraInfo[$index]"/>
                <input class="text" ng-show="whichType(extraInfo.type)" ng-model="person[n].extraInfo[$index]"/>
            </div>
        </div>
        <hr/>
        <div align="right">
            <button class="btn btn-primary" ng-click="saveSingleRegistration()">Send påmelding</button>
        </div>
    </div>
    <div ng-show="loading">
        <i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom" id="spinner"></i>
        <span class="sr-only">Loading...</span>
    </div>
</div>
</div>

</body>
</html>

</body>
</html>
