<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 17.02.2016
  Time: 09.26
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
    <spring:url value="resources/js/app/courseOverviewApp.js" var="app"/>
    <spring:url value="resources/js/controllers/courseOverviewCtrl.js" var="ctrl"/>
    <spring:url value="resources/js/service/courseService.js" var="jsonService"/>
    <script src="${app}"></script>
    <script src="${ctrl}"></script>
    <script src="${jsonService}"></script>
</head>
<body ng-app="courseOverviewApp">
<div ng-controller="OverviewCtrl" style="margin-left:3em; margin-right:3em;">
    <div class="container">
        <div class="jumbotron" id="jumbo" style="margin-bottom: 2em;">
            <h2>Søkefilter</h2>
            <label for="search">Søk etter kurs:</label>
            <input class="form-control" ng-model="search" id="search">
        </div>
    </div>
    <div class="panel-group" ng-model="panels.activePanel" role="tablist" aria-multiselectable="true" bs-collapse>
        <div class="panel panel-default" ng-repeat="panel in panels | filter:search">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title">
                    <a bs-collapse-toggle>
                        <h4 style="text-align: left;float: left">{{ panel.title }}</h4>
                        <h4 style="text-align: right;float: right">{{ panel.startDate }}</h4>
                        <hr style="clear:both;"/>
                    </a>
                </h4>
            </div>
            <div class="panel-collapse" role="tabpanel" bs-collapse-target>
                <div class="panel-body">
                    <p>{{ panel.body }}</p><br>
                    <a href="/kursogkongress/registerCourse" class="btn btn-primary">Endre</a>
                    <a href="/kursogkongress/courseStatistics" class="btn btn-primary">Statistikk</a>
                    <button type="button" class="btn btn-primary">Fakturering</button>
                    <button type="button" class="btn btn-primary">Deltakeroversikt</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
