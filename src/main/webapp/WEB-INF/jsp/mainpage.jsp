<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 18.01.2016
  Time: 10.53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Title</title>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular.min.js" data-semver="1.4.5"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <spring:url value="resources/js/viewresolver.js" var="vs"/>
    <script src="${vs}"></script>
    <script src="registerCourse.jsp"></script>
    <spring:url value="resources/html/registerEventModal.html" var="rem"/>
    <script src="${rem}"></script>
</head>
<body ng-app="viewresolver">
    <div ng-view>
    </div>
</body>
</html>
