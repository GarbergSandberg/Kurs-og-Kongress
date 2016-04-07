<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 07.04.2016
  Time: 15.33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular.min.js" data-semver="1.4.5"></script>
    <spring:url value="resources/js/app/attenderInfoApp.js" var="attender"/>
    <spring:url value="resources/js/controllers/attenderInfoCtrl.js" var="attenderCtrl"/>
    <script src="${attender}"></script>
    <script src="${attenderCtrl}"></script>
</head>
<body>
<div ng-app="attenderInfoApp">
    <div ng-controller="attenderInfoCtrl">
        {{test}}
        <input>
    </div>
</div>

</body>
</html>
