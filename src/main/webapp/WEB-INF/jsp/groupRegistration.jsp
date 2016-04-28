<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
<div ng-app="RegApp">
    <div ng-controller="AddRegCtrl" class="page-header" style="margin-left:3em; margin-right:3em;">
        <hr ng-show="!loading">
        <h1>Gruppepåmelding til {{course.title}}</h1><br>


        <!-- Arbeidsinfo -->
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
            <input type="form-control" ng-model="registration.workplace.postalcode" id="postalcode"/><br>
        </div>
        <div>
            <label for="postalcode">Sted: </label>
            <input type="form-control" ng-model="registration.workplace.location" id="location"/><br>
        </div>
        <!-- Ekstra (utvides om "checked") -->
        <label for="another">
            <input type="checkbox" id="another" ng-model="checkboxAccModel.another"/><br>
            Ønsker faktura sendt til annen adresse
        </label>
        <div ng-show="checkboxAccModel.another">
            Alternativ fakturaadresse:
            <input type="form-control" ng-model="registration.alternativeInvoiceAddress"
                   id="alternativeInvoiceAddress"
                   value="" size="40"/><br>
        </div>
        <div ng-repeat="opt in course.form.optionalWorkplace track by opt.id">
            <label>{{opt.parameter}}: </label>
            <input type="checkbox" ng-hide="whichType(opt.type)" ng-model="registration.optionalWorkplace[$index]"/>
            <input type="text" ng-show="whichType(opt.type)" ng-model="registration.optionalWorkplace[$index]"/><br>
        </div>
        <!--<div ng-repeat="opt in course.form.optionalWorkplace track by opt.id">
            <label>{{opt.parameter}}: </label>
            <input type="checkbox" ng-hide="whichType(opt.type)" ng-model="opt.answer"/>
            <input type="text" ng-show="whichType(opt.type)" ng-model="opt.answer"/><br>
        </div>-->
        <hr/>


        <h3>Personalia</h3>
        Hvor mange skal legges til?
        <select ng-model="numberOfPersons" ng-options="n for n in [] | range:1:20"></select>
        <hr/>
        <div ng-repeat="n in repeat(numberOfPersons) track by $index">
            <div class="jumbotron clearfix" id="jumbosomething">
                <form>
                    <div>
                        <label for="firstname">Fornavn: </label>
                        <input class="form-control" ng-model="person[n].firstname" id="firstname"/>
                    </div>
                    <div>
                        <label for="lastname">Etternavn: </label>
                        <input class="form-control" ng-model="person[n].lastname" id="lastname"/>
                    </div>
                    <div>
                        <label for="number">Nummer: </label>
                        <input class="form-control" ng-model="person[n].number" id="number"/>
                    </div>
                    <div>
                        <label for="email">Mail: </label>
                        <input class="form-control" ng-model="person[n].email" id="email"/>
                    </div>
                    <div>
                        <label for="birthyear">Fødselsår: </label>
                        <input class="form-control" ng-model="person[n].birthyear" id="birthyear"/>
                    </div>
                    <div ng-repeat="opt in course.form.optionalPersonalia">
                        <br>
                        <label>{{opt.parameter}}: </label>
                        <input type="checkbox" ng-hide="whichType(opt.type)" ng-init="person[n].opt[$index]='false'"
                               ng-model="person[n].opt[$index]"/>
                        <input class="text" ng-show="whichType(opt.type)" ng-model="person[n].opt[$index]"/>
                    </div>
                    <div>
                        <label ng-repeat="role in course.roles">
                            <input type="radio" name="role" ng-model="person[n].role" ng-value="role"/> {{role}}
                        </label>
                    </div>
                    <div>
                        <label>
                            <input type="radio" name="gender" value="Mann" ng-model="person[n].gender"> Mann
                            <input type="radio" name="gender" value="Kvinne" ng-model="person[n].gender"> Kvinne
                        </label>
                    </div>
                    <div>
                        <!--Ekstra (utvides om "checked") -->
                        <input type="checkbox" ng-model="checkbox[n]" ng-change="alert({{n}})">
                        <label>Bemerkning/best.nr etc?</label>
                        <div ng-show="checkbox[{{$index}}]">
                        <textarea ng-model="person[n].mark" ng-init="person[n].mark=''" class="form-control"
                                  id="description" rows="2"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div ng-if="!(($index+1) % 3)" style="clear: both"></div> <!-- "Linebreak" every 3trd div. -->
        </div>
        <div style="clear: both"></div>
        <button style="margin-left:2em;" type="button" class="btn btn-primary"
                ng-click="update(person, numberOfPersons)"> Lagre personer
        </button>

        </hr>

        <div class="list-group">
            <a class="list-group-item person" ng-repeat="reg in registrations track by $index">
                <h4 class="list-group-item-heading event">{{reg.person.firstname}} {{reg.person.lastname}}</h4>
                <p class="list-group-item-text">
                    Fødselsår: {{reg.person.birthYear}} <br>
                    Nummer: {{reg.person.number}}<br>
                    E-Mail: {{reg.person.email}}<br>
                    Merk: {{reg.person.mark}}<br>
                    Rolle: {{reg.role}}<br>
                    Kjønn: {{reg.person.gender}}<br>
                <div ng-repeat="n in person.opt track by $index">
                    {{course.form.optionalPersonalia[$index].parameter}}: {{reg.person.opt[$index]}}
                </div>


                <div ng-show="hasRoommate(reg.person)" data-placement="bottom" data-type="info"
                     data-animation="am-fade-and-scale" bs-tooltip="tooltip">
                    Rom: {{getPersonName(reg.accomondation.roommateID)}}
                    <button type="button" ng-model="tooltip.checked" class="btn btn-default btn-xs"
                            ng-click="removeRoom(reg)">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </button>
                </div>
                </p>
                <button type="button" class="btn btn-default btn-sm" ng-click="removePerson(reg)">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Slett person
                </button>
            </a>
        </div>

        <hr>


        <h3>Overnatting</h3>
        <label for="accomodation">
            <input type="checkbox" id="accomodation" ng-model="checkboxAccModel.c1"/>
            Ønsker overnatting?</label>

        <div ng-show="checkboxAccModel.c1">
            <label> Velg person: </label>
            <select ng-options="registration.person as registration.person.firstname for registration in registrations | filter:checkIfHasRoom"
                    ng-model="firstPersonRoom"></select>
            <br>
            <label>Velg hotell: </label>
            <div ng-repeat="newacc in course.hotels">
                <button class="btn btn-md"
                        ng-class="colorAccomondation(newacc) ? 'btn-primary' : 'btn-default'"
                        ng-click="selectAccomondation(newacc)"><label>{{newacc.name}}</label>
                    <h5>Pris dobbeltrom: {{newacc.doubleprice}}</h5><h5>Pris enkeltrom:
                        {{newacc.singleprice}}</h5>
                </button>
            </div>
            <br>
            <label>
                <input type="radio" name="roomType" ng-model="checkboxAccModel.rad" ng-value="true"/>
                Dobbeltrom
                <input type="radio" name="roomType" ng-model="checkboxAccModel.rad" ng-value="false"/>
                Enkeltrom
            </label>
            <br>
            <br>
            <div ng-show="checkboxAccModel.c1 && checkboxAccModel.rad">
                <label> Deler rom med: </label>
                <select ng-model="secondPersonRoom"
                        ng-options="registration.person as registration.person.firstname for registration in registrations | filter:checkIfHasRoom | filter:checkIfSelected "></select>
                <br>
                <br>
            </div>

            <form name="datepickerForm" class="form-inline" role="form" ng-show="checkboxAccModel.c1">
                <!-- http://mgcrea.github.io/angular-strap/#/datepickers -->
                <div class="form-group">
                    <label class="control-label"><i class="fa fa-calendar"></i> <i class="fa fa-arrows-h"></i> <i
                            class="fa fa-calendar"></i> Ankomst- og avreisedato </label><br><br>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" ng-model="newacc.fromDate"
                               placeholder="From" bs-datepicker>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" ng-model="newacc.toDate"
                               placeholder="Until" bs-datepicker>
                    </div>
                </div>
            </form>
            <br>
            <div ng-show="checkboxAccModel.rad">
                <button style="margin-left:2em;" type="button" class="btn btn-primary"
                        ng-click="saveRoom(newacc, firstPersonRoom, secondPersonRoom)"> Lagre
                </button>
            </div>
            <div ng-hide="checkboxAccModel.rad">
                <button style="margin-left:2em;" type="button" class="btn btn-primary"
                        ng-click="saveRoom(newacc, firstPersonRoom)"> Lagre
                </button>
            </div>
        </div>
        <hr/>


        <h3>Påmelding faglig program</h3>
        <label>
            <input type="checkbox" name="allDays" ng-model="allDaysCheck" value="allDays" ng-click="wholeCourse()">
            Hele
            kurset
        </label> <br>
        <label ng-repeat="date in dateArray">
            <input type="checkbox" name="selectedDays[]" value="{{date}}"
                   ng-checked="selectedDays.indexOf(date) > -1"
                   ng-click="selectDay(date)"> {{date | date:'EEEE'}}
        </label>


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
            <hr/>
        </table>
        <h3>Påmelding arrangementer</h3>
        <table class="table event">
            <tr ng-repeat="date in dateArray">
                <td align="center" class="session">
                    {{date | date:'EEEE'}} <p>{{date | date:'dd-MM-yyyy'}}<br>
                </td>
                <td ng-repeat="event in course.events" ng-if="sameDate(date, event.date)"> <!--  -->
                    <button ng-class="colorEvent(event) ? 'btn btn-primary btn-block': 'btn btn-default btn-block'"
                            ng-click="selectEvent(event)">
                        {{event.title}} <h5>{{event.price}}</h5>
                    </button>
                </td>

                <!-- <button class="btn btn-lg" name="selectedEvents[]" value="{{event}}"
                            ng-checked="selectedEvent.indexOf(event) > -1"
                            ng-click="selectEvent(event)"
                            ng-class="colorEvent(event) ? 'btn btn-primary btn-block': 'btn btn-default btn-block'">
                        {{event.title}}
                    </button> -->
            </tr>
        </table>
        <h3>Ekstrainfo</h3>
        <div ng-if="course.extraInfo !== undefined">
            balbalbla.
        </div>
        <div ng-repeat="extraInfo in course.form.extraInfo">
            <label>{{extraInfo.parameter}}: </label>
            <input type="checkbox" ng-if="!whichType(extraInfo.type)" ng-model="registration.extraInfo[$index]"
                   ng-init="registration.extraInfo[$index]='false'" value="false"/>
            <input type="text" ng-if="whichType(extraInfo.type)" ng-model="registration.extraInfo[$index]"
                   ng-init="registration.extraInfo[$index]=''" value=""/><br>
        </div>
        <br>
        <button style="margin-left:2em;" type="button" class="btn btn-primary"
                ng-click="saveGroupRegistration()"> Send påmelding
        </button>
    </div>
    <div ng-show="loading">
        <i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom" id="spinner"></i>
        <span class="sr-only">Loading...</span>
    </div>
</div>
</body>
</html>