<%--
  Created by IntelliJ IDEA.
  User: Lars
  Date: 10.02.16
  Time: 11.03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="margin-left" uri="http://www.springframework.org/tags/form" %>
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
    <spring:url value="resources/js/personService.js" var="personService"/>
    <script src="${appJs}"></script>
    <script src="${appEventService}"></script>
    <script src="${personService}"></script>
</head>
<body>
<div ng-app="RegApp">
    <div ng-controller="AddRegCtrl" style="margin-left:3em; margin-right:3em;">

        <div class="page-header">
            <h1>Påmelding til kurset "Tittel"</h1><br>
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
    </div>
    <div ng-controller="AddPersonCtrl" style="margin-left:3em; margin-right:3em;">

        <h3>Personalia</h3>
        <select ng-model="numberOfPersons" ng-options="n for n in [] | range:1:20"></select>
        <hr/>

        <div style="display: inline-block;" ng-repeat="n in repeat(numberOfPersons)">
            <div class="form-group col-xs-4">
                <form novalidate class="simple-form"> <!-- Skal hær være en repeat for hver attributt som finnes for en person (varierer fra kurs til kurs) -->
                    <label for="firstname">Fornavn:</label>
                    <input type="form-control" ng-model="person[$index].firstname" id="firstname"/><br>
                    <label for="lastname">Etternavn: </label>
                    <input type="form-control" ng-model="person[$index].lastname" id="lastname"/><br>
                    <label for="birthyear">Fødselsår: </label>
                    <input type="form-control" ng-model="person[$index].birthyear" id="birthyear"/><br>
                    <label for="title">Tittel: </label>
                    <input type="form-control" ng-model="person[$index].title" id="title"/><br>
                    <label for="number">Nummer: </label>
                    <input type="form-control" ng-model="person[$index].number" id="number"/><br>
                    <label for="mail">Mail: </label>
                    <input type="form-control" ng-model="person[$index].mail" id="mail"/><br>
                    <br>
                </form>
            </div>
        </div>
        <hr/>
        <button style="margin-left:2em;" type="button" class="btn btn-primary" ng-click="update(person)"> Lagre </button>

        <div class="list-group">
            <a class="list-group-item person" ng-repeat="person in persons">
                <h4 class="list-group-item-heading event">{{person.firstname}} {{person.lastname}}</h4>
                <p class="list-group-item-text">
                    Fødselsår: {{person.birthyear}} <br>
                    Tittel: {{person.title}}<br>
                    Nummer: {{person.number}}<br>
                    E-Mail: {{person.email}}<br>
                </p>
            </a>
        </div>

    </div>


    <!--

            <!-- Ekstra (utvides om "checked")
            <label for="mark">
                <input type="checkbox" id="mark" ng-model="checkboxAccModel.mark"/>
                Bemerkning/spesiell diett/best.nr/kode etc?
            </label>
            <div ng-show="checkboxAccModel.mark">
                Beskrivelse:
                <textarea ng-model="person.description" class="form-control" id="description" rows="3"></textarea>
            </div>
            <hr/>
        </div>-->
</div>


</body>
</html>
