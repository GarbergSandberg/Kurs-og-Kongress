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

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
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
        <h1>Fakturaoversikt for {{course.title}}</h1><br>
        <h4>Kursutgifter</h4>
        <table class="table">
            <tr style="background-color:gray;color:black;">
                <td style="text-align: left;">Hva </td>
                <td style="text-align: center;">Pris </td>
                <td style="text-align: center;">Antall </td>
                <td style="text-align: right;">Total </td>
            </tr>
            <!-- Kursavgifter og dagpakke -->
            <tr>
                <td style="text-align: left;">Kursavgift (helt kurs) </td>
                <td style="text-align: center;">{{course.courseFee}} kr</td>
                <td style="text-align: center;">{{numberOfcourseFee.number}} </td>
                <td style="text-align: right;">{{numberOfcourseFee.total}} kr</td>
            </tr>
            <tr>
                <td style="text-align: left;">Kursavgift (dag) </td>
                <td style="text-align: center;">{{course.courseSingleDayFee}} kr</td>
                <td style="text-align: center;">{{numberOfcourseSingleDayFee.number}} </td>
                <td style="text-align: right;">{{numberOfcourseSingleDayFee.total}} kr</td>
            </tr>
            <tr>
                <td style="text-align: left;">Dagpakke </td>
                <td style="text-align: center;">{{course.dayPackage}}  kr</td>
                <td style="text-align: center;">{{numberOfDaypackages.number}} </td>
                <td style="text-align: right;">{{numberOfDaypackages.total}} kr</td>
            </tr>
            <tr style="border-top: thick double #000000; color:black; font-weight: bold;">
                <td style="text-align: left;">Sum </td>
                <td style="text-align: center;"> </td>
                <td style="text-align: center;"> </td>
                <td style="text-align: right;">{{getTotal(0)}} kr</td>
            </tr>
        </table>
        <br>
        <h4>Arrangementsutgifter</h4>
        <table class="table">
            <tr style="background-color:gray;color:black;">
                <td style="text-align: left;">Hva </td>
                <td style="text-align: center;">Pris </td>
                <td style="text-align: center;">Antall </td>
                <td style="text-align: right;">Total </td>
            </tr>
            <!-- Arrangementer -->
            <tr ng-repeat="event in numberOfEvents">
                <td style="text-align: left;">{{event.title}} </td>
                <td style="text-align: center;">{{event.price}} kr</td>
                <td style="text-align: center;">{{event.number}} </td>
                <td style="text-align: right;">{{event.total}} kr</td>
            </tr>
            <tr style="border-top: thick double #000000; color:black; font-weight: bold;">
                <td style="text-align: left;">Sum </td>
                <td style="text-align: center;"> </td>
                <td style="text-align: center;"> </td>
                <td style="text-align: right;">{{getTotal(1)}} kr</td>
            </tr>
        </table>
        <br>
        <h4>Totalt</h4>
        <table class="table">
            <tr style="border-bottom: thick double #000000; color:black; font-weight: bold;">
                <td style="text-align: left;">Total sum </td>
                <td style="text-align: center;"> </td>
                <td style="text-align: center;"> </td>
                <td style="text-align: right;">{{getTotal()}} kr</td>
            </tr>
        </table>
        <br>
        <br>
    </div>
</div>

</body>
</html>
