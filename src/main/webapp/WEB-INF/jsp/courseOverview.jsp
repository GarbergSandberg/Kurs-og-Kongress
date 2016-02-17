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
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-animate.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-sanitize.min.js" data-semver="1.4.5"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.tpl.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/docs/angular-strap.docs.tpl.js" data-semver="v2.3.7"></script>
    <spring:url value="resources/js/courseOverviewApp.js" var="app"/>
    <spring:url value="resources/js/courseService.js" var="jsonService"/>
    <script src="${app}"></script>
    <script src="${jsonService}"></script>
</head>
<body ng-app="courseOverviewApp">
<div ng-controller="OverviewCtrl">
        <div class="panel-group" ng-model="panels.activePanel" role="tablist" aria-multiselectable="true" bs-collapse>
            <div class="panel panel-default" ng-repeat="panel in panels">
                <div class="panel-heading" role="tab">
                    <h4 class="panel-title">
                        <a bs-collapse-toggle>
                            {{ panel.title }}
                        </a>
                    </h4>
                </div>
                <div class="panel-collapse" role="tabpanel" bs-collapse-target>
                    <div class="panel-body">
                        {{ panel.body }}
                    </div>
                </div>
            </div>
        </div>
</div>
</body>
</html>
