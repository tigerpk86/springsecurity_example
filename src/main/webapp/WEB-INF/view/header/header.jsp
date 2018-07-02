<%@ page import="org.springframework.security.core.Authentication" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2018. 6. 30.
  Time: 오후 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%--<sec:authentication property="principal.username" var="loginId" scope="request"/>--%>


<%
    Authentication auth = (Authentication) request.getUserPrincipal();
    String username = "";
    if(auth == null) {

    } else {
        Object principal = auth.getPrincipal();
        username = principal.toString();
    }
%>

<div id="header">
    <div class="username">

        Wecome,
        <sec:authorize access="hasRole('ROLE_USER')">
            <strong><sec:authentication property="principal.username"/></strong>
            <%--<%=username%>--%>
        </sec:authorize>

    </div>

    <c:url value="/login/logout" var="logoutUrl"/>
    <li><a href="${logoutUrl}"}>Log Out</a></li>

</div>