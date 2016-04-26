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
    <spring:url value="resources/js/app/sessionRegisterApp.js" var="session"/>
    <spring:url value="resources/js/controllers/statisticsCtrl.js" var="statisticsCtrl"/>
    <spring:url value="resources/js/service/statisticsService.js" var="statisticsService"/>
    <spring:url value="resources/js/service/courseService.js" var="courseService"/>
    <spring:url value="resources/js/service/sessionRegisterService.js" var="sessionRegisterService"/>
    <spring:url value="resources/js/service/eventRegisterService.js" var="eventRegisterService"/>
    <spring:url value="resources/js/service/hotelService.js" var="hotelService"/>
    <spring:url value="resources/js/service/attenderInfoService.js" var="attenderInfoService"/>
    <script src="${session}"></script>
    <script src="${statisticsCtrl}"></script>
    <script src="${statisticsService}"></script>
    <script src="${courseService}"></script>
    <script src="${sessionRegisterService}"></script>
    <script src="${eventRegisterService}"></script>
    <script src="${hotelService}"></script>
    <script src="${attenderInfoService}"></script>
</head>
<body>
<div ng-app="registerApp">
    <div ng-controller="statisticsCtrl" style="margin-left:3em; margin-right:3em;">
        Kurs-"økonomi" for {{course.title}}
        <table class="table">
            <tr style="background-color:gray;color:black;">
                <td style="text-align: left;">Hva </td>
                <td style="text-align: center;">Pris </td>
                <td style="text-align: center;">Antall </td>
                <td style="text-align: right;">Total </td>
            </tr>
            <tr>
                <td style="text-align: left;">Kursavgift (helt kurs) </td>
                <td style="text-align: center;">{{course.courseFee}} </td>
                <td style="text-align: center;">{{numberOfcourseFee.number}} </td>
                <td style="text-align: right;">{{numberOfcourseFee.total}}</td>
            </tr>
            <tr>
                <td style="text-align: left;">Kursavgift (dag) </td>
                <td style="text-align: center;">{{course.courseSingleDayFee}} </td>
                <td style="text-align: center;">{{numberOfcourseSingleDayFee.number}} </td>
                <td style="text-align: right;">{{numberOfcourseSingleDayFee.total}}</td>
            </tr>
            <tr>
                <td style="text-align: left;">Dagpakke </td>
                <td style="text-align: center;">{{course.dayPackage}} </td>
                <td style="text-align: center;">{{numberOfDaypackages.number}} </td>
                <td style="text-align: right;">{{numberOfDaypackages.total}}</td>
            </tr>
        </table>
        <br>
        <br>
    </div>
</div>

</body>
</html>
