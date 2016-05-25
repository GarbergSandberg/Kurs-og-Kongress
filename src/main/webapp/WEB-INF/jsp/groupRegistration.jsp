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
    <link rel="stylesheet" href="resources/css/register.css">
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-animate.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-sanitize.min.js" data-semver="1.4.5"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.tpl.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/docs/angular-strap.docs.tpl.js" data-semver="v2.3.7"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.0/angular-messages.min.js"></script>
    <spring:url value="resources/js/locale.js" var="locale"/>
    <spring:url value="resources/js/app/registrationApp.js" var="appJs"/>
    <spring:url value="resources/js/app/courseOverviewApp.js" var="myApp"/>
    <spring:url value="resources/js/service/eventRegisterService.js" var="appEventService"/>
    <spring:url value="resources/js/service/personService.js" var="personService"/>
    <spring:url value="resources/js/service/regService.js" var="regService"/>
    <spring:url value="resources/js/controllers/addRegCtrl.js" var="regCtrl"/>
    <script src="${locale}"></script>
    <script src="${appJs}"></script>
    <script src="${myApp}"></script>
    <script src="${appEventService}"></script>
    <script src="${personService}"></script>
    <script src="${regService}"></script>
    <script src="${regCtrl}"></script>
</head>
<body>
<div ng-app="RegApp">
    <div ng-controller="AddRegCtrl" class="page-header" style="margin-left:3em; margin-right:3em; margin-bottom: 3em;">
        <div ng-show="!loading">
            <h1>Gruppepåmelding til {{course.title}}</h1><br>

            <hr/>
            <div class="form-horizontal" align="center" style="text-align: left; max-width: 90%; min-width: 60%;">
                <div class="form-group">
                    <form name="workForm">
                        <h3>Arbeidsgiverinfo</h3>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Arbeidsplass </label>
                            <div class="col-sm-6">
                                <input autocomplete="off" class="form-control"
                                       ng-model="registration.workplace.companyName" name="name" required/>
                                <div ng-messages="workForm.name.$error" ng-show="workForm.name.$touched">
                                    <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Adresse </label>
                            <div class="col-sm-6">
                                <input autocomplete="off" class="form-control" ng-model="registration.workplace.address"
                                       name="address" required/>
                                <div ng-messages="workForm.address.$error" ng-show="workForm.address.$touched">
                                    <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Postnr </label>
                            <div class="col-sm-6">
                                <input autocomplete="off" class="form-control"
                                       ng-model="registration.workplace.postalcode"
                                       ng-minlength="4" ng-maxlength="4" type="number" name="postalcode" required/>
                                <div ng-messages="workForm.postalcode.$error" ng-show="workForm.postalcode.$touched">
                                    <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                                    <div ng-message="minlength" style="color:red;">Postnummeret må være 4 siffer.</div>
                                    <div ng-message="maxlength" style="color:red;">Postnummeret må være 4 siffer.</div>
                                    <div ng-message="number" style="color:red;">Postnummeret må bestå av tall.</div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Sted </label>
                            <div class="col-sm-6">
                                <input autocomplete="off" class="form-control"
                                       ng-model="registration.workplace.location" name="location" required/>
                                <div ng-messages="workForm.location.$error" ng-show="workForm.location.$touched">
                                    <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                                </div>
                            </div>
                        </div> <!-- Ekstra (utvides om "checked") -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Ønsker annen fakturaadresse</label>
                            <div class="col-sm-6">
                                <input type="checkbox" id="another" ng-model="checkboxAccModel.another"/>
                                <div ng-messages="workForm.another.$error" ng-show="workForm.another.$touched">
                                    <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div ng-show="checkboxAccModel.another">
                                <label class="col-sm-4 control-label">Adresse, postnr og sted</label>
                                <div class="col-sm-6">
                                    <input autocomplete="off" class="form-control"
                                           ng-model="registration.alternativeInvoiceAddress"
                                           id="alternativeInvoiceAddress" value="" size="40"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" ng-repeat="opt in course.form.optionalWorkplace track by opt.id">
                            <label class="col-sm-4 control-label">{{opt.parameter}} </label>
                            <div class="col-sm-6">
                                <input class="form-control" ng-model="registration.optionalWorkplace[$index]"
                                       type="checkbox" ng-if="!whichType(opt.type)"
                                       ng-init="registration.extraInfo[$index]='false'"/>
                            </div>
                            <div class="col-sm-6" ng-if="whichType(opt.type)" ng-form="work_opt_{{$index}}">
                                <input autocomplete="off" class="form-control"
                                       ng-model="registration.optionalWorkplace[$index]"
                                       ng-init="registration.extraInfo[$index]=''" name="option" required/>
                                <div ng-messages="work_opt_{{$index}}.option.$error"
                                     ng-show="work_opt_{{$index}}.option.$touched">
                                    <div ng-message="required" style="color:red;" align="center">Vennligst fyll inn
                                        feltet.
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <hr/>

            <h3>Personalia</h3>
            <div align="center">
                <br> <br>
                <div>
                    <label>Hvor mange skal legges til? &nbsp</label>
                    <select ng-model="numberOfPersons" ng-options="n for n in [] | range:1:20"></select>
                </div>
                <div ng-repeat="n in repeat(numberOfPersons) track by $index">
                    <form name="personForm">
                        <div class="jumbotron clearfix" id="jumbosomething" align="center">
                            <div>
                                <label>Fornavn: </label>
                                <input autocomplete="off" class="form-control" ng-model="person[n].firstname"
                                       name="firstname" required/>
                                <div ng-messages="personForm.firstname.$error"
                                     ng-show="personForm.firstname.$touched">
                                    <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                                </div>
                            </div>
                            <div>
                                <label>Etternavn: </label>
                                <input autocomplete="off" class="form-control" ng-model="person[n].lastname"
                                       name="lastname" required/>
                                <div ng-messages="personForm.lastname.$error"
                                     ng-show="personForm.lastname.$touched">
                                    <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                                </div>
                            </div>
                            <div>
                                <label>Telefonnummer: </label>
                                <input autocomplete="off" class="form-control" ng-model="person[n].number"
                                       ng-minlength="8"
                                       ng-maxlength="8" name="number" required/>
                                <div ng-messages="personForm.number.$error" ng-show="personForm.number.$touched">
                                    <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                                    <div ng-message="number" style="color:red;">Telefonnummeret må være et tall.
                                    </div>
                                    <div ng-message="minlength" style="color:red;">Telefonnummeret må bestå av 8
                                        siffer.
                                    </div>
                                    <div ng-message="maxlength" style="color:red;">Telefonnummeret må bestå av 8
                                        siffer.
                                    </div>
                                </div>
                            </div>
                            <div>
                                <label>Mail: </label>
                                <input type="email" autocomplete="off" class="form-control"
                                       ng-model="person[n].email" name="email"
                                       required/>
                                <div ng-messages="personForm.email.$error" ng-show="personForm.email.$touched">
                                    <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                                    <div ng-message="email" style="color:red;">Må være email-format.</div>
                                </div>
                            </div>
                            <div>
                                <label>Fødselsår: </label>
                                <input autocomplete="off" class="form-control" ng-model="person[n].birthYear"
                                       ng-minlength="4" ng-maxlength="4" name="birthYear" required/>
                                <div ng-messages="personForm.birthYear.$error"
                                     ng-show="personForm.birthYear.$touched">
                                    <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                                    <div ng-message="number" style="color:red;">Fødselsåret må være et årstall.
                                    </div>
                                    <div ng-message="minlength" style="color:red;">Fødselsåret må bestå av 4 siffer.
                                    </div>
                                    <div ng-message="maxlength" style="color:red;">Fødselsåret må bestå av 4 siffer.
                                    </div>
                                </div>
                            </div>


                            <div ng-repeat="opt in course.form.optionalPersonalia">
                                <div class="form-group" ng-form="pers_opt_{{$index}}">
                                    <label>{{opt.parameter}}: </label>
                                    <input type="checkbox" ng-if="!whichType(opt.type)"
                                           ng-init="person[n].opt[$index]='false'"
                                           ng-model="person[n].opt[$index]"/>
                                    <input class="form-control" ng-if="whichType(opt.type)"
                                           ng-model="person[n].opt[$index]" name="optPers" required/>
                                    <div ng-if="whichType(opt.type)" ng-messages="pers_opt_{{$index}}.optPers.$error"
                                         ng-show="pers_opt_{{$index}}.optPers.$touched">
                                        <div ng-message="required" style="color:red;" align="center">Vennligst fyll inn
                                            feltet.
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--<div ng-messages="personForm.optPers.$error" ng-show="personForm.optPers.$touched">
                                <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                            </div> -->
                            <div>
                                <label ng-repeat="role in course.roles">
                                    <input type="radio" name="role" ng-model="person[n].role" ng-value="role" required/>{{role}}
                                    &nbsp
                                </label>
                            </div>
                            <div>
                                <label>
                                    <input type="radio" name="gender" value="Mann" ng-model="person[n].gender" required>
                                    Mann
                                    <input type="radio" name="gender" value="Kvinne" ng-model="person[n].gender">
                                    Kvinne
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
                        </div>
                        <div ng-if="!(($index+1) % 3)" style="clear: both"></div> <!-- "Linebreak" every 3trd div. -->
                        <span ng-if="personForm.$valid && personForm.$dirty"
                              ng-init="validatePersons(personForm.$valid)"></span>
                            <span ng-if="!personForm.$valid && personForm.$dirty"
                                  ng-init="validatePersons(personForm.$valid)"></span>
                    </form>
                </div>
                <div style="clear: both"></div>
                <div ng-if="personForm.$invalid"> Alle personaliafelter må fylles ut før personene kan lagres.</div>
                <button align="center" class="btn btn-primary" ng-if="numberOfPersons > 0"
                        ng-click="update(person, numberOfPersons)" ng-disabled="!validPersons"> Lagre personer
                </button>
                <br> <br>
            </div>

            </hr ng-if="numberOfPersons > 0">

            <div class="list-group" align="center">
                <a class="list-group-item person" ng-repeat="reg in registrations track by $index">
                    <label>{{reg.person.firstname}} {{reg.person.lastname}}</label>
                    <h5> Fødselsår: {{reg.person.birthYear}} </h5>
                    <h5> Telefon: {{reg.person.phonenumber}}</h5>
                    <h5>E-Mail: {{reg.person.email}}</h5>
                    <h5>Merk: {{reg.person.mark}}</h5>
                    <h5>Rolle: {{reg.role}}</h5>
                    <h5> Kjønn: {{reg.person.gender}}</h5>
                    <div ng-repeat="n in person.opt track by $index">
                        <h5>{{course.form.optionalPersonalia[$index].parameter}}: {{reg.person.opt[$index]}} </h5>
                    </div>


                    <!-- <h4 class="list-group-item-heading event">{{reg.person.firstname}} {{reg.person.lastname}}</h4>
                     <p class="list-group-item-text">
                         Fødselsår: {{reg.person.birthYear}} <br>
                         Nummer: {{reg.person.number}}<br>
                         E-Mail: {{reg.person.email}}<br>
                         Merk: {{reg.person.mark}}<br>
                         Rolle: {{reg.role}}<br>
                         Kjønn: {{reg.person.gender}}<br>
                     <div ng-repeat="n in person.opt track by $index">
                         {{course.form.optionalPersonalia[$index].parameter}}: {{reg.person.opt[$index]}}
                     </div> -->


                    <div ng-show="hasRoommate(reg.person)" data-placement="right" data-type="info"
                         data-animation="am-fade-and-scale" bs-tooltip="tooltip">
                        <h5> Rom: {{getPersonName(reg.accomondation.roommateID)}} </h5>
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
            <div style="clear: both"></div>

            <hr>

            <div align="left" ng-if="course.hotels.length > 0">
                <h3>Overnatting</h3>
                <div align="center">
                    <div class="form-group">
                        <br> <br>
                        <label class="control-label">Huk av om dere ønsker overnatting &nbsp</label>
                        <input type="checkbox" id="accomodation" ng-model="checkboxAccModel.c1"/>
                    </div>
                    <div ng-if="course.hotels.length > 0" ng-show="checkboxAccModel.c1">
                        <div class="form-group">
                            <br>
                            <label> Velg person: </label>
                            <select ng-model="firstPersonRoom"
                                    ng-options="registration.person as registration.person.firstname for registration in registrations | filter:checkIfHasRoom"></select>
                        </div>
                        <div class="form-group">
                            <br>
                            <label>Velg hotell: </label>
                            <div ng-repeat="newacc in course.hotels">
                                <button
                                        ng-class="colorAccomondation(newacc) ? 'btn btn-primary test' : 'btn btn-default test'"
                                        ng-click="selectAccomondation(newacc)"><label>{{newacc.name}}</label>
                                    <h5>Pris dobbeltrom {{newacc.doubleprice}} kr</h5>
                                    <h5>Pris enkeltrom {{newacc.singleprice}} kr</h5>
                                </button>
                                <div ng-if="!(($index+1) % 3)" style="clear: both"></div>
                            </div>
                        </div>
                        <div style="clear: both"></div>
                        <div class="form-group" align="center">
                            <label>
                                <input type="radio" name="roomType" ng-model="checkboxAccModel.rad"
                                       ng-value="true"/>
                                Dobbeltrom &nbsp
                                <input type="radio" name="roomType" ng-model="checkboxAccModel.rad"
                                       ng-value="false"/>
                                Enkeltrom
                            </label>
                            <br>
                            <br>
                            <div ng-show="checkboxAccModel.c1 && checkboxAccModel.rad">
                                <label> Deler rom med: </label>
                                <select ng-model="secondPersonRoom"
                                        ng-options="registration.person as registration.person.firstname for registration in registrations | personFilter:firstPersonRoom | filter:checkIfHasRoom"></select>
                                <br>
                                <br>
                            </div>
                            <form name="datepickerForm" class="form-inline" role="form"
                                  ng-show="checkboxAccModel.c1">
                                <!-- http://mgcrea.github.io/angular-strap/#/datepickers -->
                                <div class="form-group">
                                    <label class="control-label"><i class="fa fa-calendar"></i> <i
                                            class="fa fa-arrows-h"></i>
                                        <i
                                                class="fa fa-calendar"></i> Ankomst- og avreisedato </label><br><br>
                                    <div class="form-group col-sm-6">
                                        <input type="text" class="form-control" ng-model="newacc.fromDate"
                                               placeholder="Fra" bs-datepicker>
                                    </div>
                                    <div class="form-group col-sm-6">
                                        <input type="text" class="form-control" ng-model="newacc.toDate"
                                               placeholder="Til" bs-datepicker>
                                    </div>
                                </div>
                            </form>
                            <br>
                            <div ng-show="checkboxAccModel.rad">
                                <button style="margin-left:2em;" type="button" class="btn btn-primary"
                                        ng-click="saveRoom(newacc, firstPersonRoom, secondPersonRoom)"> Lagre
                                    overnatting
                                </button>
                            </div>
                            <div ng-hide="checkboxAccModel.rad">
                                <button type="button" class="btn btn-primary"
                                        ng-click="saveRoom(newacc, firstPersonRoom)">
                                    Lagre overnatting
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <br> <br>
                <hr/>
            </div>

            <h3>Påmelding faglig program</h3>
            <h3 style="text-align: center">
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
            <br>
            <h3 style="text-align: center">
                <small>Velg dager</small>
            </h3>
            <hr>
            <div align="center" class="form-group">
                <label>
                    <input type="checkbox" name="allDays" ng-model="allDaysCheck" value="allDays"
                           ng-click="wholeCourse()">
                    Hele kurset
                </label>
            </div>
            <div align="center" class="form-group">
                <span ng-repeat="date in dateArray">
                    <input type="checkbox" name="selectedDays[]" value="{{date}}"
                           ng-checked="selectedDays.indexOf(date) > -1"
                           ng-click="selectDay(date)"> {{date | date:'EEEE dd.MM.yyyy'}} &nbsp
                </span>
            </div>
            <br> <br>
            <hr>
            <div ng-if="course.sessions.length > 0">
            <h3>Påmelding sesjoner</h3>
            <h3>
                <small> Velg hvilke sesjoner gruppen skal delta på ved å trykke på den enkelte sesjonen</small>
            </h3>
            <table class="table session">
                <tr ng-repeat="date in dateArray" ng-init="sessionTableRow = $index">
                    <td align="center" class="session">
                        {{date | date:'EEEE'}} <p>{{date | date:'dd.MM.yyyy'}}<br>
                    </td>
                    <td ng-repeat="session in course.sessions | sessionFilter: sessionTableRow: dateArray | orderBy:'hourMinuteStart'">
                        <button ng-class="colorSession(session) ? 'btn btn-primary btn-block': 'btn btn-default btn-block'"
                                ng-click="selectSession(session)"> {{session.title}} <h5>({{session.startTime |
                            date:'HH:mm'}} - {{session.endTime | date:'HH:mm'}})</h5>
                        </button>
                    </td>
                </tr>
                <hr/>
            </table>
            <br> <br>
            <hr>
            </div>
            <div ng-if="course.events.length > 0">
            <h3>Påmelding arrangementer</h3>
            <h3>
                <small> Velg hvilke arrangementer gruppen skal delta på ved å trykke på det enkelte arrangementet
                </small>
            </h3>
            <table class="table session">
                <tr ng-repeat="date in dateArray">
                    <td align="center" class="session">
                        {{date | date:'EEEE'}} <p>{{date | date:'dd.MM.yyyy'}}<br>
                    </td>
                    <td ng-repeat="event in course.events" ng-if="sameDate(date, event.date)"> <!--  -->
                        <button ng-class="colorEvent(event) ? 'btn btn-primary btn-block': 'btn btn-default btn-block'"
                                ng-click="selectEvent(event)">
                            {{event.title}} <h5>{{event.price | number: 2}} kr</h5>
                        </button>
                    </td>
                </tr>
                <hr/>
            </table>
            </div>
            <div class="form-horizontal" align="center" style="text-align: left; max-width: 90%; min-width: 60%;">
                <div align="left" ng-if="course.form.extraInfo.length > 0">
                    <h3>Ekstrainfo</h3>
                </div>
                <div align="center" ng-if="course.form.extraInfo.length > 0">
                    <div ng-repeat="extraInfo in course.form.extraInfo">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">{{extraInfo.parameter}}: </label>
                            <div class="col-sm-6">
                                <input class="form-control" type="checkbox" ng-if="!whichType(extraInfo.type)"
                                       ng-init="registration.extraInfo[$index]='false'"
                                       ng-model="registration.extraInfo[$index]"/>
                                <div ng-if="whichType(extraInfo.type)" ng-form="extraInfoForm">
                                <input autocomplete="off" class="form-control"
                                       ng-model="registration.extraInfo[$index]"
                                       ng-init="registration.extraInfo[$index]=''" name="extInfo" required/>
                                <div ng-messages="extraInfoForm.extInfo.$error"
                                     ng-show="extraInfoForm.extInfo.$touched">
                                    <div ng-message="required" style="color:red;">Vennligst fyll inn feltet.</div>
                                </div>
                            </div>
                            </div>
                        </div>
                        </form>
                    </div>
                </div>
            </div>
            <hr/>
            <div>
                <div align="right">
                    <span ng-if="!workForm.$valid || registrations.length == 0 || selectedDays.length == 0">Personalia, arbeidsgiverinfo eller valgte dager mangler &nbsp </span>
                    <button class="btn btn-primary" ng-click="saveGroupRegistration()"
                            ng-disabled="!workForm.$valid || registrations.length == 0 || selectedDays.length == 0">
                        Send påmelding
                    </button>
                </div>
            </div>
            <hr>
        </div>
        <div ng-show="loading">
            <i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom" id="spinner"></i>
            <span class="sr-only">Loading...</span>
        </div>
    </div>
</div>
</body>
</html>