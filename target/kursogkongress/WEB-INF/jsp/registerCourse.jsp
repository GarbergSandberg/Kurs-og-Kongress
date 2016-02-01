<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <spring:url value="resources/js/sessionRegisterApp.js" var="appJs" />
    <spring:url value="resources/js/sessionRegisterService.js" var="appService" />
    <spring:url value="resources/html/registerSessionModal.html" var="modalTemplate" />
    <script src="${appJs}"></script>
    <script src="${appService}"></script>
    <script src="${modalTemplate}"></script>
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
                        <input type="text" class="form-control" ng-model="course.startDate" placeholder="Start" name="date" bs-datepicker>
                    </div>
                    <div class="form-group col-md-6" ng-class="{'has-error': datepickerForm.date.$invalid}">
                        <input type="text" class="form-control" ng-model="course.endDate" placeholder="Slutt" name="date" bs-datepicker>
                    </div>
            </div>
            <label for="maxnumber">Maks antall deltakere:</label>
            <input class="form-control" ng-model="course.maxNumber" id="maxnumber">
            <label for="location">Kurssted:</label>
            <input class="form-control" ng-model="course.location" id="location" >
            <label for="role">Legg til en rolle:</label>
            <div class="input-group">
                <input class="form-control" ng-model="course.role" id="role" >
                <span class="input-group-btn">
                    <button class="btn btn-secondary" type="button" ng-click="addRole(course.role)">Legg til</button>
                </span>
            </div>
            <ul>
                <li ng-repeat="role in roles" id="{{role}}">{{role}}</li>
            </ul>
            <div class="page-header">
                <h4>Parallelle sesjoner</h4>
            </div>
        </div>
        <div ng-controller="AddSessionCtrl">
            <table class="table">
                <tr ng-repeat="date in dates" >
                    <td  align="center">
                        {{date.id}}
                    </td>
                    <td>
                        <button type="button" id="{{date.id}}" ng-click="passBtnId(date.id)"
                                class="btn btn-primary btn-block"
                                data-animation="am-fade-and-slide-top"
                                data-template-url=${modalTemplate}
                                        bs-modal="modal">+
                        </button>
                    </td>
                    <td ng-repeat="session in sessions | filter:date.id">
                        <button id="sessionButton" data-ng-attr-id="btnId" type="button"
                                class="btn btn-primary btn-block"
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
    </div>
        <a href="<c:url value="/event"/>">Event</a>
    </body>
</html>