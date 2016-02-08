<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <!--This is important-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <spring:url value="resources/js/sessionRegisterApp.js" var="appJs"/>
    <spring:url value="resources/js/sessionRegisterService.js" var="appService"/>
    <spring:url value="resources/html/registerSessionModal.html" var="modalTemplate"/>
    <spring:url value="resources/js/eventRegisterService.js" var="appEventService"/>
    <spring:url value="resources/html/registerEventModal.html" var="eventModal"/>
    <spring:url value="resources/js/courseService.js" var="jsonService"/>
    <script src="${appJs}"></script>
    <script src="${appService}"></script>
    <script src="${jsonService}"></script>
    <script src="${modalTemplate}"></script>
    <script src="${appEventService}"></script>
    <script src="${eventModal}"></script>
</head>
<body>
<div ng-app="registerApp">
    <div ng-controller="AddCourseCtrl" style="margin-left:3em; margin-right:3em;">
        <div class="page-header">
            <h1>Legg til kurs</h1><br>
            <h4>Generell informasjon om kurset</h4>
        </div>
        <label for="title">Tittel:</label>
        <input class="form-control" ng-model="course.title" id="title">
        <label for="description">Beskrivelse:</label>
        <textarea ng-model="course.description" class="form-control" id="description" rows="3"></textarea>
        <div class="form-group">
            <label class="control-label"><i class="fa fa-calendar"></i><b> Start og sluttdato</b></label><br>
            <div class="form-group col-md-6" ng-class="{'has-error': datepickerForm.date.$invalid}">
                <input type="text" class="form-control" ng-model="course.startDate" placeholder="Start" name="date"
                       bs-datepicker>
            </div>
            <div class="form-group col-md-6" ng-class="{'has-error': datepickerForm.date.$invalid}">
                <input type="text" class="form-control" ng-model="course.endDate" placeholder="Slutt" name="date"
                       bs-datepicker>
            </div>
        </div>
        <label for="maxnumber">Maks antall deltakere:</label>
        <input class="form-control" ng-model="course.maxNumber" id="maxnumber" type="number">
        <label for="location">Kurssted:</label>
        <input class="form-control" ng-model="course.location" id="location">
        <label for="role">Legg til en rolle:</label>
        <div class="input-group">
            <input class="form-control" ng-model="role" id="role">
                <span class="input-group-btn">
                    <button class="btn btn-primary" type="button" ng-click="addRole(role); role = '';">Legg til</button>
                </span>
        </div>
        <table class="table">
            <tr ng-repeat="role in roles">
                <td>
                    {{role}}
                </td>
                <td style="">
                    <button type="button" id="{{role}}" ng-click="removeRole(role)" class="close" aria-label="Close">
                        <span aria-hidden="true">&times;</span></button>
                </td>
            </tr>
        </table>
        <div class="page-header">
            <h4>Parallelle sesjoner</h4>
        </div>
        <div ng-controller="AddSessionCtrl">
            <table class="table session">
                <tr ng-repeat="date in dates">
                    <td align="center" class="session">
                        {{date.day}}/{{date.month}}-{{date.year}}<br>
                        {{date.weekday}}<br>
                    </td>
                    <td class="session">
                        <button type="button" id="{{date.id}}" ng-click="passBtnId(date.id)"
                                class="btn btn-primary btn-block"
                                data-animation="am-fade-and-slide-top"
                                data-template-url=${modalTemplate}
                                        bs-modal="modal">+
                        </button>
                    </td>
                    <td class="session" ng-repeat="session in sessions | filter:date.id">
                        <button id="sessionButton" data-ng-attr-id="btnId" type="button"
                                class="btn btn-secondary btn-block"
                                data-animation="am-fade-and-slide-top"
                                data-template-url=${modalTemplate}
                                        bs-modal="modal">{{session.title}}
                        </button>
                    </td>
                </tr>
            </table>
        </div>
        <div class="page-header">
            <h4>Arrangementer</h4>
        </div>
        <div ng-controller="AddEventCtrl">
            <div class="row">
                <div class="col-sm-3 col-md-6">
                    <ul>
                        <button type="button" id="{{addEventButton}}"
                                class="btn btn-primary btn-block"
                                data-animation="am-fade-and-slide-top"
                                data-template-url=${eventModal}
                                        bs-modal="modal">+
                        </button>
                    </ul>
                    <ul ng-repeat="event in events" style="margin-top: 1em;">
                        <button id="eventButton" data-ng-attr-id="btnId" type="button"
                                class="btn btn-secondary btn-block"
                                data-animation="am-fade-and-slide-top"
                                data-template-url=${eventModal}
                                        bs-modal="modal">{{event.title}}
                        </button>
                    </ul>
                </div>
                <div class="col-sm-9 col-md-6">
                </div>
            </div>
        </div>
        <div class="page-header">
            <h4>Ekstra informasjon til påmeldingsskjema</h4>
        </div>
        <div ng-controller="RegistrationCtrl">
            <div class="container">
                <div class="jumbotron">
                    <h4>Generelt <span class="label label-primary">Info</span></h4>
                    <div class="checkbox">
                        <label><input type="checkbox" ng-model="checkboxModel.hotel">Kurs og Kongress kan ordne
                            hotel</label><br>
                        <label><input type="checkbox" ng-model="checkboxModel.airplane">Kurs og Kongress kan booke
                            fly</label>
                    </div>
                    <h4>Tilleggsspørsmål     <span class="label label-primary">Info</span></h4>
                    <button id="input" ng-click="buttonResolver('input')" class="{{class[0]}}">Input</button>
                    <button id="checkbox" ng-click="buttonResolver('checkbox')" class="{{class[1]}}">Checkbox</button><br>

                    <span class="{{hidden[0]}}">
                        <div class="input-group">
                            <input class="form-control" ng-model="inputQuestion" id="inputQuestion" placeholder="Skriv inn tekst som skal stå foran input">
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="button"
                            ng-click="addInputQuestion(inputQuestion, 'Input'); inputQuestion = '';">Legg til</button>
                            </span>
                        </div>
                    </span>
                    <span class="{{hidden[1]}}">
                        <div class="input-group">
                            <input class="form-control" ng-model="inputQuestion" placeholder="Skriv inn tekst som skal stå foran checkbox">
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="button"
                                        ng-click="addInputQuestion(inputQuestion, 'Checkbox'); inputQuestion = '';">Legg til</button>
                            </span>
                        </div>
                    </span>
                    <table class="table">
                        <tr ng-repeat="question in inputQuestions">
                            <td>
                                {{question.question}} ({{question.type}})
                            </td>
                            <td style="">
                                <button type="button" id="{{question.question}}" ng-click="removeInputQuestion(question.question)"
                                        class="close" aria-label="Close">
                                    <span aria-hidden="true">&times;</span></button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

        </div>
        <button type="button" class="btn btn-primary" ng-click="save(course)">Lagre</button>
    </div>
</div>

</body>
</html>