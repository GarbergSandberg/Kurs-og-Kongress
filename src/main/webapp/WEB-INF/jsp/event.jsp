<%--
  Created by IntelliJ IDEA.
  User: Lars
  Date: 27.01.16
  Time: 09.39
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html ng-app="eventApp">
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
    <spring:url value="/resources/js/eventRegisterApp.js" var="appJs" />
    <spring:url value="/resources/html/registerEventModal.html" var="modalTemplate" />
    <script src="${appJs}"></script>
    <script src="${modalTemplate}"></script>
</head>
<body>
<div ng-controller="AddEventCtrl">
    <button type="button"
            class="btn btn-lg btn-primary"
            data-animation="am-fade-and-slide-top"
            data-template-url=${modalTemplate}
                    bs-modal="modal">Klikk for a legge til Arrangement.
        <br>
    </button>
</div>

<div ng-controller="AddEventInfoCtrl">
    <pre>{{event | json}}</pre>
    <button type="button" ng-click="update">
    </button>
</div>

<form:form action="endrePersoner" method="post" modelAttribute="personFormBackingBean">
    <h5>Checkboxene trengs kun å brukes ved sletting og henting, ikke ved oppdatering av personer</h5>
    <table border="1" width="100%">
    <tr>
    <th>Personnummer</th>
    <th>Fornavn</th>
    <th>Etternavn</th>
    <th>Velg </th>
    </tr>

    <c:forEach var="person" items="${personFormBackingBean.allePersoner}" varStatus="status">

        <tr>
            <td><c:out value="${person.firstname}"/>
                <form:hidden path="allePersoner[${status.index}].firstname" />
            </td>

            <td> <form:input path="allePersoner[${status.index}].lastname" />
                <form:errors path="allePersoner[${status.index}].lastname" />
            </td>
            <td> <form:checkbox path="valgtePersoner" value="${person}" /> </td>
        </tr>
    </c:forEach>
    </table>
    </form:form>
</body>
</html>
