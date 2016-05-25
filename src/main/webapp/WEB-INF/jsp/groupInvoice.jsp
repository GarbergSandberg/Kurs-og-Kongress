<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="//cdn.jsdelivr.net/fontawesome/4.3.0/css/font-awesome.css">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="//mgcrea.github.io/angular-strap/styles/libs.min.css">
    <link rel="stylesheet" href="//mgcrea.github.io/angular-strap/styles/docs.min.css">
    <link rel="stylesheet" href="resources/css/color.css">
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-animate.min.js" data-semver="1.4.5"></script>
    <script src="//cdn.jsdelivr.net/angularjs/1.4.5/angular-sanitize.min.js" data-semver="1.4.5"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/dist/angular-strap.tpl.js" data-semver="v2.3.7"></script>
    <script src="//mgcrea.github.io/angular-strap/docs/angular-strap.docs.tpl.js" data-semver="v2.3.7"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.0/angular-messages.min.js"></script>
    <spring:url value="resources/js/app/sessionRegisterApp.js" var="session"/>
    <spring:url value="resources/js/controllers/attenderInfoCtrl.js" var="attenderInfoCtrl"/>
    <spring:url value="resources/js/service/statisticsService.js" var="statisticsService"/>
    <spring:url value="resources/js/service/courseService.js" var="courseService"/>
    <spring:url value="resources/js/service/sessionRegisterService.js" var="sessionRegisterService"/>
    <spring:url value="resources/js/service/eventRegisterService.js" var="eventRegisterService"/>
    <spring:url value="resources/js/service/hotelService.js" var="hotelService"/>
    <spring:url value="resources/js/service/attenderInfoService.js" var="attenderInfoService"/>
    <script src="${session}"></script>
    <script src="${attenderInfoCtrl}"></script>
    <script src="${statisticsService}"></script>
    <script src="${courseService}"></script>
    <script src="${sessionRegisterService}"></script>
    <script src="${eventRegisterService}"></script>
    <script src="${hotelService}"></script>
    <script src="${attenderInfoService}"></script>
</head>
<body>
<div ng-app="registerApp" style="margin-left:7em; margin-right:7em;">
    <div ng-controller="attenderInfoCtrl">
        <table class="table">
            <tr>
                <td>
                    <h3>Kurs & Kongresservice</h3>
                </td>
                <td>
                </td>
            </tr>
            <tr class="tableRowHighlight">
                <td>Fakturainfo</td>

                <td></td>
            </tr>
            <tr>
                <td>
                    Arbeidsplass
                </td>
                <td style="text-align: right">
                    {{selectedParticipant.workplace.companyName}}
                </td>
            </tr>
            <tr>
                <td>
                    Fakturaadresse
                </td>
                <td style="text-align: right">
                    <span ng-if="selectedParticipant.alternativeInvoiceAddress">{{selectedParticipant.alternativeInvoiceAddress}}</span>
                    <span ng-if="!selectedParticipant.alternativeInvoiceAddress">{{selectedParticipant.workplace.address}}, {{selectedParticipant.workplace.postalcode}} {{selectedParticipant.workplace.location}}</span>
                </td>
            </tr>
        </table>
        <table class="table">
            <tr class="tableRowHighlight">
                <td>Type utgift</td>
                <td style="text-align: center">Antall</td>
                <td style="text-align: center">Pris</td>
                <td style="text-align: right">Total</td>
            </tr>
            <tr ng-if="groupRegCourseFee.number > 0">
                <td>
                    Kursavgift
                </td>
                <td style="text-align: center">
                    {{groupRegCourseFee.number}} stk
                </td>
                <td style="text-align: center">
                    {{course.courseFee | number : 2}} kr
                </td>
                <td style="text-align: right">
                    {{groupRegCourseFee.total | number : 2}} kr
                </td>
            </tr>
            <tr ng-if="groupRegCourseSingleDayFee.number > 0">
                <td>
                    Kursavgift enkeltdag
                </td>
                <td style="text-align: center">
                    {{groupRegCourseSingleDayFee.number}} stk
                </td>
                <td style="text-align: center">
                    {{course.courseSingleDayFee | number : 2}} kr
                </td>
                <td style="text-align: right">
                    {{groupRegCourseSingleDayFee.total | number : 2}} kr
                </td>
            </tr>
            <tr ng-if="groupRegDaypackage.number > 0">
                <td>
                    Dagpakke
                </td>
                <td style="text-align: center">
                    {{groupRegDaypackage.number}} stk
                </td>
                <td style="text-align: center">
                    {{course.dayPackage | number : 2}} kr
                </td>
                <td style="text-align: right">
                    {{groupRegDaypackage.total | number : 2}} kr
                </td>
            </tr>
            <tr ng-repeat="event in course.events">
                <span ng-if="groupRegEvents[$index].number > 0">
                    <td>
                        {{event.title}}
                    </td>
                    <td style="text-align: center">
                        {{groupRegEvents[$index].number}} stk
                    </td>
                    <td style="text-align: center">
                        {{event.price | number : 2}} kr
                    </td>
                    <td style="text-align: right">
                        {{groupRegEvents[$index].total | number : 2}} kr
                    </td>
                </span>
            </tr>
            <tr style="border-top-style: solid; color:black; font-weight: bold">
                <td>
                    Totalt
                </td>
                <td></td>
                <td></td>
                <td style="text-align: right">
                    {{getTotal() | number : 2}} kr
                </td>
            </tr>
            <!--
            <tr ng-repeat="event in selectedParticipant.attendingEvents">
                <td>
                    {{event.title}}
                </td>
                <td style="text-align: right">
                    {{event.price | number : 2}} kr
                </td>
            </tr>
            <tr style="border-top-style: solid; color:black; font-weight: bold">
                <td>
                    Totalt
                </td>
                <td style="text-align: right">
                    {{selectedParticipant.totalAmount | number : 2}} kr
                </td>
            </tr> -->
        </table>
    </div>
</div>
</body>
</html>
