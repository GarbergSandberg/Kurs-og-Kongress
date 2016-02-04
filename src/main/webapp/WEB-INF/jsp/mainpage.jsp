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
</head>
<body>

<!--
< form:form action="endrePersoner" method="post" modelAttribute="personFormBackingBean">
    <h5>Checkboxene trengs kun å brukes ved sletting og henting, ikke ved oppdatering av personer</h5>
    <table border="1" width="100%">
        <tr>
            <th>Personnummer</th>
            <th>Fornavn</th>
            <th>Etternavn</th>
            <th>Velg</th>
        </tr>

        < c:forEach var="person" items="$ {personFormBackingBean.allePersoner}" varStatus="status">

            <tr>
                <td>< c:out value="$ {person.firstname}"/>
                    < form:hidden path="allePersoner[$ {status.index}].firstname"/>
                </td>

                <td>< form:input path="allePersoner[$ {status.index}].lastname"/>
                    < form:errors path="allePersoner[$ {status.index}].lastname"/>
                </td>
                <td>< form:checkbox path="valgtePersoner" value="$ {person}"/></td>
            </tr>
        </c :forEach>
    </table>
</ form:form>
-->

</body>
</html>
