<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
</head>
<body>
<div ng-app="RegApp">
    <div ng-controller="AddRegCtrl" style="margin-left:3em; margin-right:3em;">

        <div class="page-header">
            <h1>Gruppepåmelding</h1><br>
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







    </div>
    <div ng-controller="AddPersonCtrl" style="margin-left:3em; margin-right:3em;">

        <h3>Personalia</h3>
        <select ng-model="numberOfPersons" ng-options="n for n in [] | range:1:20"></select>
        <hr/>

        <div style="display: inline-block; border-style: groove; max-width: 230px;"
             ng-repeat="n in repeat(numberOfPersons)">
            <form>
                <!-- Skal hær være en repeat for hver attributt som finnes for en person (varierer fra kurs til kurs) -->
                <label for="firstname">Fornavn:</label><br>
                <input type="text" ng-model="person[$index].firstname" id="firstname"/><br>
                <label for="lastname">Etternavn: </label><br>
                <input type="text" ng-model="person[$index].lastname" id="lastname"/><br>
                <label for="birthyear">Fødselsår: </label><br>
                <input type="text" ng-model="person[$index].birthyear" id="birthyear"/><br>
                <label for="title">Tittel: </label><br>
                <input type="text" ng-model="person[$index].title" id="title"/><br>
                <label for="number">Nummer: </label><br>
                <input type="text" ng-model="person[$index].number" id="number"/><br>
                <label for="mail">Mail: </label><br>
                <input type="text" ng-model="person[$index].mail" id="mail"/><br>
                <!--Ekstra (utvides om "checked") -->
                <input type="checkbox" ng-model="checkbox[$index]" ng-change="alert({{$index}})">
                Bemerkning/spesiell diett/best.nr/kode etc?
                <div ng-show="checkbox[{{$index}}]">
                    Beskrivelse:
                        <textarea ng-model="person.description" class="form-control" id="description"
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
                    Fødselsår: {{person.birthyear}} <br>
                    Tittel: {{person.title}}<br>
                    Nummer: {{person.number}}<br>
                    E-Mail: {{person.email}}<br>
                    Rom: {{person.roommate.firstname}}
                </p>
                <button type="button" class="btn btn-default btn-sm" ng-click="removePerson(person)">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Slett person
                </button>
                <button type="button" class="btn btn-default btn-xs" ng-click="removeRoom(person)">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Slett rom-kamerat.
                </button>
            </a>
        </div>
    </div>
    <div ng-controller="AddRegCtrl" style="margin-left:3em; margin-right:3em;">
        <h3>Overnatting</h3>
        <label for="accomodation">
            <input type="checkbox" id="accomodation" ng-model="checkboxAccModel.c1"/>
            Ønsker overnatting?</label>

        <div ng-show="checkboxAccModel.c1">
            <label> Velg person: </label>
            <select ng-options="person as person.firstname for person in persons | filter:checkIfHasRoom" ng-model="firstPersonRoom">{{person.firstname}}
                {{person.lastname}}</select>
            <br>
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
                        <input type="text" class="form-control" ng-model="fromDate" data-min-date="{{minDate}}"
                               data-max-date="{{maxDate}}" placeholder="From" bs-datepicker>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" ng-model="untilDate" data-max-date="{{maxDate}}"
                               data-min-date="{{minDate}}" placeholder="Until" bs-datepicker>
                    </div>
                </div>
            </form> <br>
            <div ng-show="checkboxAccModel.rad">
                <button style="margin-left:2em;" type="button" class="btn btn-primary" ng-click="saveRoom(firstPersonRoom, secondPersonRoom)"> Lagre </button>
            </div>
            <div ng-hide="checkboxAccModel.rad">
                <button style="margin-left:2em;" type="button" class="btn btn-primary" ng-click="saveRoom(firstPersonRoom)"> Lagre </button>
            </div>
        </div>
        <hr/>
    </div>


</div>


</body>
</html>
