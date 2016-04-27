<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 25.04.2016
  Time: 12.15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="//cdn.jsdelivr.net/fontawesome/4.3.0/css/font-awesome.css">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="//mgcrea.github.io/angular-strap/styles/libs.min.css">
    <link rel="stylesheet" href="//mgcrea.github.io/angular-strap/styles/docs.min.css">
    <link rel="stylesheet" href="resources/css/courseOverview.css">
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-animate.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-sanitize.min.js" data-semver="1.4.5"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.tpl.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/docs/angular-strap.docs.tpl.js" data-semver="v2.3.7"></script>
    <spring:url value="resources/js/app/publicRegistrationApp.js" var="app"/>
    <spring:url value="resources/js/controllers/publicRegistrationCtrl.js" var="ctrl"/>
    <spring:url value="resources/js/service/publicRegistrationService.js" var="jsonService"/>
    <script src="${app}"></script>
    <script src="${ctrl}"></script>
    <script src="${jsonService}"></script>
</head>
<body ng-app="publicRegistrationApp">
<div ng-controller="PublicRegistrationCtrl" style="margin-left:3em; margin-right:3em;">
    <div ng-show="!loading">
        <div class="page-header">
            <h1>Påmelding til kurs</h1>
        </div>
        <div class="panel-group" ng-model="panels.activePanel" role="tablist" aria-multiselectable="true" bs-collapse>
            <div class="panel panel-default" ng-repeat="panel in panels | orderBy:'panel.startDate'">
                <div class="panel-heading" role="tab">
                    <h4 class="panel-title">
                        <a bs-collapse-toggle>
                            <h4>{{ panel.title }}</h4>
                        </a>
                    </h4>
                </div>
                <div class="panel-collapse" role="tabpanel" bs-collapse-target>
                    <div class="panel-body">
                        <ul class ="list-group" id="infolist">
                            <li class="list-group-item">
                                <p style="font-weight: bold;">Beskrivelse:</p>
                                <p>{{ panel.body }}</p>
                            </li>
                            <li class="list-group-item">
                                <p>Kurset starter <span style="font-weight: bold;">{{ panel.startDate | date:'dd-MM-yyyy'}}</span></p>
                                <p>Kurset slutter <span style="font-weight: bold;">{{ panel.endDate | date:'dd-MM-yyyy'}}</span></p>
                            </li>
                            <li class="list-group-item">
                                Det er {{panel.remainingSlots}} ledige plasser igjen på kurset. <br>
                            </li>
                            <li class="list-group-item">
                                <button ng-click="registration(panel.courseID, 'single')" class="btn btn-primary">Enkeltpåmelding</button>
                                <button ng-click="registration(panel.courseID, 'group')" class="btn btn-primary">Gruppepåmelding</button>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div ng-show="loading">
        <i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom" id="spinner"></i>
        <span class="sr-only">Loading...</span>
    </div>
</div>
</body>
</html>
