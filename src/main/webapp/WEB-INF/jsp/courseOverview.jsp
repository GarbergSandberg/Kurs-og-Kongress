<%@ page import="domain.*" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <spring:url value="resources/js/controllers/courseOverviewNotAdminCtrl.js" var="ctrl2"/>
    <spring:url value="resources/js/service/courseService.js" var="jsonService"/>
    <script src="${app}"></script>
    <script src="${ctrl}"></script>
    <script src="${jsonService}"></script>
    <script src="${ctrl2}"></script>
    <%
        User user = (User) session.getAttribute("user");
    %>
</head>
<body>
<div ng-app="courseOverviewApp">
    <% if (user.isAdmin()) {%>
    <div ng-controller="OverviewCtrl">
        <div ng-show="!loading">
            <div class="jumbotron clearfix" id="jumbo">
                <h2>Søkefilter</h2>
                <label for="search">Søk etter kurs:</label>
                <input class="form-control" ng-model="search" id="search">
                <div class="form-group col-xs-6">
                    <label for="sel1">År:</label>
                    <select class="form-control" ng-options="year as year for year in years" ng-model="selectedYear"
                            id="sel1"></select>
                </div>
                <div class="form-group col-xs-6">
                    <label for="sel2">Måned:</label>
                    <select class="form-control" ng-disabled="!selectedYear"
                            ng-options="months.indexOf(month) as month for month in months" ng-model="selectedMonth"
                            id="sel2"></select>
                </div>
            </div>
            <div class="panel-group" ng-model="panels.activePanel" role="tablist" aria-multiselectable="true"
                 bs-collapse style="margin-left:3em; margin-right:3em;">
                <div class="page-header">
                    <h2>Kurs</h2>
                </div>
                <div class="panel panel-default"
                     ng-repeat="panel in panels | filter:search | filter:selectedMonthFilter | filter:selectedYearFilter | orderBy: 'panel.startDate'">
                    <div class="panel-heading" role="tab">
                        <h4 class="panel-title">
                            <a bs-collapse-toggle>
                                <h4>{{ panel.title }}</h4>
                            </a>
                        </h4>
                    </div>
                    <div class="panel-collapse" role="tabpanel" bs-collapse-target>
                        <div class="panel-body">
                            <ul class="list-group" id="infolist">
                                <li class="list-group-item">
                                    <p style="font-weight: bold;">Beskrivelse:</p>
                                    <p>{{ panel.body }}</p>
                                </li>
                                <li class="list-group-item">
                                    <p>Kurset starter <span style="font-weight: bold;">{{ panel.startDate | date:'dd-MM-yyyy'}}</span>
                                    </p>
                                    <p>Kurset slutter <span style="font-weight: bold;">{{ panel.endDate | date:'dd-MM-yyyy'}}</span>
                                    </p>
                                </li>
                                <li class="list-group-item">
                                    <span data-placement="top" data-type="default"
                                          data-animation="am-fade-and-scale" bs-tooltip="change">
                                        <button ng-click="editCourse(panel.courseID)" ng-model="change.checked"
                                                class="btn btn-default btn-lg">
                                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                        </button>
                                    </span>
                                    <span data-placement="top" data-type="default"
                                          data-animation="am-fade-and-scale" bs-tooltip="statistics">
                                        <button ng-click="getStatistics(panel.courseID)" ng-model="statistics.checked"
                                                class="btn btn-default btn-lg"><span
                                                class="glyphicon glyphicon-stats" aria-hidden="true"></span>
                                        </button>
                                    </span>
                                    <span data-placement="top" data-type="default"
                                          data-animation="am-fade-and-scale" bs-tooltip="public">
                                        <button ng-click="enableRegistration(panel.courseID)" ng-model="public.checked"
                                              ng-class="changeColor(panel.courseID)"><span
                                               class="glyphicon glyphicon-globe" aria-hidden="true"></span>
                                         </button>
                                    </span>
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
    <%
    } else {
    %>
    <div ng-controller="OverviewCtrlLite" style="margin-left:3em; margin-right:3em;">
        <div ng-show="!loading">
            <div class="page-header">
                <h1>Kursoversikt</h1>
            </div>
            <div class="panel-group" ng-model="panels.activePanel" role="tablist" aria-multiselectable="true"
                 bs-collapse>
                <div class="panel panel-default" ng-repeat="panel in panels | orderBy: 'panel.startDate'">
                    <div class="panel-heading" role="tab">
                        <h4 class="panel-title">
                            <a bs-collapse-toggle>
                                <h4>{{ panel.title }}</h4>
                            </a>
                        </h4>
                    </div>
                    <div class="panel-collapse" role="tabpanel" bs-collapse-target>
                        <div class="panel-body">
                            <ul class="list-group" id="infolist">
                                <li class="list-group-item">
                                    <p style="font-weight: bold;">Beskrivelse:</p>
                                    <p>{{ panel.body }}</p>
                                </li>
                                <li class="list-group-item">
                                    <p>Kurset starter <span style="font-weight: bold;">{{ panel.startDate | date:'dd-MM-yyyy'}}</span>
                                    </p>
                                    <p>Kurset slutter <span style="font-weight: bold;">{{ panel.endDate | date:'dd-MM-yyyy'}}</span>
                                    </p>
                                </li>
                                <li class="list-group-item">
                                    <button ng-click="getStatistics(panel.courseID)" class="btn btn-default btn-lg">
                                        <span class="glyphicon glyphicon-stats" aria-hidden="true"></span></button>
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
    <%
        }
    %>
</div>
</body>
</html>