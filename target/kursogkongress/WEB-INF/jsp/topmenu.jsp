<%@ page import="domain.*" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 15.02.2016
  Time: 15.36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="resources/css/index.css">
    <link rel="stylesheet" href="resources/css/bootstrap.css">
    <link rel="stylesheet" href="resources/css/landing-page.css">
    <script>
        function resetSessionStorage(){
            sessionStorage.removeItem("cid");
        }
    </script>
    <%
    User user = (User)session.getAttribute("user");
    %>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container topnav">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand topnav" href="/kursogkongress/courseOverview">Kurs- og Kongresservice</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="/kursogkongress/publicRegistrations">Påmeldingskjema</a>
                </li>
                <li>
                    <a href="/kursogkongress/courseOverview">Kursoversikt</a>
                </li>
                <%
                    if(user.isAdmin()){
                %>
                <li>
                    <a onclick="resetSessionStorage()" href="/kursogkongress/registerCourse">Legg til nytt kurs</a>
                </li>
                <li>
                    <a href="/kursogkongress/createUser">Administrer</a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
