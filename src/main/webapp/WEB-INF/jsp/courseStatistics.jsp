<%@ page import="domain.*" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Lars
  Date: 15.04.2016
  Time: 14.33
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
    <link rel="stylesheet" href="resources/css/color.css">
    <link rel="stylesheet" href="resources/css/courseOverview.css">
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
    <%
        User user = (User)session.getAttribute("user");
    %>
</head>
<body>
<div ng-app="registerApp">
    <div ng-controller="statisticsCtrl" style="margin-left:3em; margin-right:3em;">
        <div ng-show="!loading">
            <!-- INFORMASJON OM KURSET -->
            <div class="container">
                <div class="jumbotron clearfix" id="jumbo">
                    <%
                      if(user.isAdmin()){
                    %>
                    <button type="button" ng-click="courseEconomics()" class="btn btn-default"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> | Økonomisk rapport</button>
                    <button type="button" ng-click="sendEmail()" class="btn btn-default"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span> | Kontakt deltakere</button>
                    <hr>
                    <%
                        }
                    %>
                    <div class="col-sm-6 col-md-6 col-lg-6">
                        <label> <span style="float: left;">Kurs: </span> </label><span style="float: right;">{{course.title}} </span>
                        <br>
                        <label> <span style="float: left;">Beskrivelse: </span> </label><span style="float: right;">{{course.description}} </span>
                        <br>
                        <label> <span style="float: left;">Sted: </span> </label><span style="float: right;">{{course.location}}</span>
                        <br>
                        <label> <span style="float: left;">Startdato: </span> </label><span style="float: right;">{{course.startDate | date: 'dd-MM-yyyy'}} </span>
                        <br>
                        <label> <span style="float: left;">Sluttdato: </span> </label><span style="float: right;">{{course.endDate | date: 'dd-MM-yyyy'}}</span>
                        <br>
                        <label> <span style="float: left;">Hotell: </span> </label>
                        <span ng-repeat="hotel in course.hotels" style="float: right;">{{hotel.name}}, &nbsp <br></span><br>
                    </div>
                    <!--<hr width="1" size="200" /> -->
                    <div class="col-sm-6 col-md-6 col-lg-6">

                        <label> <span style="float: left;">Maks antall deltakere: </span> </label>
                        <span style="float: right;">{{course.maxNumber}} stk</span> <br>
                        <label> <span style="float: left;">Antall påmeldte: </span> </label>
                        <span style="float: right;">{{countReg}} stk</span> <br>
                        <label> <span style="float: left;">Kursavgift (hele kurset): </span> </label>
                        <span style="float: right;">{{course.courseFee}} kr</span> <br>
                        <label> <span style="float: left;">Kursavgift (per dag): </span> </label>
                        <span style="float: right;">{{course.courseSingleDayFee}} kr</span> <br>
                        <label> <span style="float: left;">Dagpakkepris: </span> </label>
                        <span style="float: right;">{{course.dayPackage}} kr</span> <br>
                        <br>
                    </div>
                    <hr>
                    &nbsp
                    <hr>
                    <div class="col-sm-6 col-md-6 col-lg-6">
                        <label> Sesjoner: </label>
                        <span ng-repeat="session in course.sessions" style="float: right;">
                            {{session.title}}, &nbsp; {{session.startTime | date: 'dd.MM.yyyy'}}, &nbsp {{session.startTime | date: 'HH:mm'}} - {{session.endTime | date:'HH:mm'}}<br> </span> <br>
                        </span>
                    </div>
                    <!--<hr width="1" size="200" /> -->
                    <div class="col-sm-6 col-md-6 col-lg-6">
                        <label> Events: </label>
                        <span ng-repeat="event in course.events" style="float: right;">
                            {{event.title}}, &nbsp; {{event.date | date: 'dd.MM.yyyy'}}, &nbsp {{event.time | date: 'HH:mm'}}<br> </span> <br>
                        </span>
                    </div>
                </div>
            </div>


            <!-- INFORMASJON OM DE PÅMELDTE -->
            <div class="container">
                <div class="jumbotron" id="jumbo" style="margin-bottom: 2em;">
                    <h2>Søkefilter</h2>
                    <label for="search">Søk etter deltaker:</label>
                    <input class="form-control" ng-model="search" id="search">
                    <br>
                    <table class="table">
                        <tr ng-if="!(filtered == 0)" class="tableRowHighlight">
                            <td style="text-align: left;">Navn: </td>
                            <td style="text-align: center;">Arbeidsplass: </td>
                            <td style="text-align: right;"> </td>
                        </tr>
                        <tr ng-if="filtered == 0">
                            <td></td>
                            <td>
                                <h5 style="text-align: center;">Ingen deltakere</h5>
                            </td>
                            <td></td>
                        </tr>
                        <tr ng-repeat="registration in filtered = (registrations | filter:search)">
                            <td style="text-align: left">
                                {{registration.person.firstname}} {{registration.person.lastname}}
                            </td>
                            <td style="text-align: center">
                                {{registration.workplace.companyName}}
                            </td>
                            <td style="text-align: right">
                                <button type="button" class="btn btn-default" ng-click="showInfo(registration)">Oversikt</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div ng-show="loading">
            <i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom" id="spinner"></i>
            <span class="sr-only">Loading...</span>
        </div>
        </div>
</div>
</body>
</html>
