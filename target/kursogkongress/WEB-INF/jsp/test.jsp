<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html ng-app="registerApp">
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

    <!--This is important-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <spring:url value="/resources/js/sessionRegisterApp.js" var="appJs" />
    <spring:url value="/resources/html/registerSessionModal.html" var="modalTemplate" />
    <script src="${appJs}"></script>
    <script src="${modalTemplate}"></script>
</head>
    <body>
        <div ng-controller="AddSessionCtrl">
            <table>
                <tr>
                    <td ng-repeat="session in sessions">
                        <button data-ng-attr-id="btnId" type="button"
                                class="btn btn-lg btn-primary"
                                data-animation="am-fade-and-slide-top"
                                data-template-url=${modalTemplate}
                                        bs-modal="modal">{{session.title}}
                            <br>
                            <small>{{session.startTime}}</small><br>
                            <small>{{session.endTime}}</small>
                        </button>
                    </td>
                </tr>
            </table>
            <button type="button"
                    class="btn btn-lg btn-primary"
                    data-animation="am-fade-and-slide-top"
                    data-template-url=${modalTemplate}
                            bs-modal="modal">Click to toggle modal
                <br>
                <small>(using an object)</small>
            </button>

        </div>

        <div ng-controller="AddInfoCtrl">
            <pre>{{sessions | json}}</pre>
            <button type="button" ng-click="update">
            </button>
        </div>
    </body>
</html>