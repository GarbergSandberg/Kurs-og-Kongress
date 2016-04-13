<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
</head>
<body>
<div ng-app="RegApp">
    <div ng-controller="AddRegCtrl" class="page-header" style="margin-left:3em; margin-right:3em;">
        <h1>Gruppepåmelding til "{{course.title}}"</h1><br>


        <!-- Arbeidsinfo -->
        <h3>Arbeidsgiverinfo</h3>
        <div>
            <label for="name">Arbeidsplass: </label>
            <input type="form-control" ng-model="workplace.companyName" id="name"/><br>
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
            <input type="checkbox" id="another" ng-model="checkboxAccModel.another"/><br>
            Ønsker faktura sendt til annen adresse
        </label>
        <div ng-show="checkboxAccModel.another">
            Alternativ fakturaadresse:
            <input type="form-control" ng-model="workplace.facturaAddress" id="facturaAdress" size="40"/><br>
        </div>
        <div ng-repeat="opt in course.form.optionalWorkplace">
            <label for="opt">{{opt.parameter}}: </label>
            <input type="checkbox" ng-hide="whichType(opt.type)" ng-model="workplace.opt[$index]" id="opt"/>
            <input type="text" ng-show="whichType(opt.type)" ng-model="person.opt[$index]" id="opt"/><br>
        </div>
        <hr/>


        <h3>Personalia</h3>
        Hvor mange skal legges til?<select ng-model="numberOfPersons" ng-options="n for n in [] | range:1:20"></select>
        <hr/>

        <div style="display: inline-block; border-style: groove; max-width: 230px;"
             ng-repeat="n in repeat(numberOfPersons)">
            <form>
                <div>
                    <label for="firstname">Fornavn: </label>
                    <input type="form-control" ng-model="person[n].firstname" id="firstname"/>
                </div>
                <div>
                    <label for="lastname">Etternavn: </label>
                    <input type="form-control" ng-model="person[n].lastname" id="lastname"/>
                </div>
                <div>
                    <label for="number">Nummer: </label>
                    <input type="form-control" ng-model="person[n].number" id="number"/>
                </div>
                <div>
                    <label for="mail">Mail: </label>
                    <input type="form-control" ng-model="person[n].mail" id="mail"/>
                </div>
                <div>
                    <label for="birthyear">Fødselsår: </label>
                    <input type="form-control" ng-model="person[n].birthyear" id="birthyear"/>
                </div>
                <div ng-repeat="opt in course.form.optionalPersonalia">
                    <label for="opt">{{opt.parameter}}: </label>
                    <input type="checkbox" ng-hide="whichType(opt.type)" ng-model="person[n].opt[$index]" id="opt"/><br>
                    <input type="text" ng-show="whichType(opt.type)" ng-model="person[n].opt[$index]" id="opt"/><br><br>
                </div>
                <label ng-repeat="role in course.roles">
                    <input type="radio" name="role" ng-model="person[n].role" ng-value="role"/> {{role}}
                </label>
                <label>
                    <input type="radio" name="gender" value="Mann" ng-model="person[n].gender"> Mann
                    <input type="radio" name="gender" value="Kvinne" ng-model="person[n].gender"> Kvinne
                </label>
                <br>
                <!--Ekstra (utvides om "checked") -->
                <input type="checkbox" ng-model="checkbox[n]" ng-change="alert({{n}})">
                Bemerkning/spesiell diett/best.nr/kode etc?
                <div ng-show="checkbox[{{$index}}]">
                    Beskrivelse:
                    <textarea ng-model="person[n].mark" class="form-control" id="description"
                              rows="3"></textarea>
                </div>
            </form>
        </div>
        <hr/>
        <button style="margin-left:2em;" type="button" class="btn btn-primary" ng-click="update(person)"> Lagre</button>

        <div class="list-group">
            <a class="list-group-item person" ng-repeat="person in persons">
                <h4 class="list-group-item-heading event">{{person.firstname}} {{person.lastname}}</h4>
                <p class="list-group-item-text">
                    Fødselsår: {{person.birthYear}} <br>
                    Nummer: {{person.phonenumber}}<br>
                    E-Mail: {{person.email}}<br>
                    Merk: {{person.mark}}<br>
                    Rolle: {{person.role}}<br>
                    Kjønn: {{person.gender}}<br>
                <div ng-repeat="n in person.opt track by $index">
                    {{course.form.optionalPersonalia[$index].parameter}}: {{person.opt[$index]}}
                </div>
                <div ng-show="hasRoommate(person)" data-placement="bottom" data-type="info"
                     data-animation="am-fade-and-scale" bs-tooltip="tooltip">
                    Rom: {{getPersonName(person.accomondation.roommateID)}}
                    <button type="button" ng-model="tooltip.checked" class="btn btn-default btn-xs"
                            ng-click="removeRoom(person)">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </button>
                </div>
                </p>
                <button type="button" class="btn btn-default btn-sm" ng-click="removePerson(person)">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Slett person
                </button>
            </a>
        </div>


        <h3>Overnatting</h3>
        <label for="accomodation">
            <input type="checkbox" id="accomodation" ng-model="checkboxAccModel.c1"/>
            Ønsker overnatting?</label>

        <div ng-show="checkboxAccModel.c1">
            <label> Velg person: </label>
            <select ng-options="person as person.firstname for person in persons | filter:checkIfHasRoom"
                    ng-model="firstPersonRoom"></select>
            <br>
            <label>Velg hotell: </label>
            <div  ng-repeat="accomondation in course.accomondations">
                    <button class="btn btn-md"
                            ng-class="colorAccomondation(accomondation) ? 'btn-primary' : 'btn-default'"
                            ng-click="selectAccomondation(accomondation)"> <label>{{accomondation.name}}</label>
                        <h5>Pris dobbeltrom: {{accomondation.doubleprice}}</h5><h5>Pris enkeltrom:
                            {{accomondation.singleprice}}</h5>
                    </button>
            </div>


            <br>
            <label>
                <input type="radio" name="roomType" ng-model="checkboxAccModel.rad" ng-value="true"/> Dobbeltrom
                <input type="radio" name="roomType" ng-model="checkboxAccModel.rad" ng-value="false"/> Enkeltrom
            </label>
            <br>
            <br>
            <div ng-show="checkboxAccModel.c1 && checkboxAccModel.rad">
                <label> Deler rom med: </label>
                <select ng-options="person2 as person2.firstname for person2 in persons  | filter:checkIfSelected | filter:checkIfHasRoom"
                        ng-model="secondPersonRoom"></select>
                <br>
                <br>
            </div>

            <form name="datepickerForm" class="form-inline" role="form" ng-show="checkboxAccModel.c1">
                <!-- http://mgcrea.github.io/angular-strap/#/datepickers -->
                <div class="form-group">
                    <label class="control-label"><i class="fa fa-calendar"></i> <i class="fa fa-arrows-h"></i> <i
                            class="fa fa-calendar"></i> Ankomst- og avreisedato </label><br><br>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" ng-model="accomondation.startDate"
                               data-min-date="{{course.startDate}}"
                               data-max-date="{{course.endDate}}" placeholder="From" bs-datepicker>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" ng-model="accomondation.endDate"
                               data-max-date="{{course.endDate}}"
                               data-min-date="{{course.startDate}}" placeholder="Until" bs-datepicker>
                    </div>
                </div>
            </form>
            <br>
            <div ng-show="checkboxAccModel.rad">
                <button style="margin-left:2em;" type="button" class="btn btn-primary"
                        ng-click="saveRoom(accomondation, firstPersonRoom, secondPersonRoom)"> Lagre
                </button>
            </div>
            <div ng-hide="checkboxAccModel.rad">
                <button style="margin-left:2em;" type="button" class="btn btn-primary"
                        ng-click="saveRoom(accomondation, firstPersonRoom)"> Lagre
                </button>
            </div>
        </div>
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
                        date:'HH:mm'}} -
                        {{session.endTime | date:'HH:mm'}})</h5>
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
                            ng-click="selectEvent(event)" ng-class="colorEvent(event) ? 'btn-primary' : 'btn-default'">
                        {{event.title}}
                    </button>
                </td>
            </tr>
        </table>
        <button style="margin-left:2em;" type="button" class="btn btn-primary"
                ng-click="saveGroupRegistration(workplace)"> Send påmelding
        </button>
    </div>
</div>
</body>
</html>
