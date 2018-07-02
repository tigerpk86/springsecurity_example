<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %><%--<%@ taglib prefix="security" uri="https://www.springframework.org/security/tags" %>--%>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2018. 6. 23.
  Time: 오후 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authentication property="principal.username" var="loginId" scope="request"/>


<html>
<head>
    <title>Title</title>
</head>
<body>

인사말 : ${greeting}
<br/>
인사말 : <sec:authentication property="principal.username"/>

<br/>

<%--<sec:authorize access="hasRole('ROLE_USER')">--%>
    <%--principal : print <sec:authentication property="principal"/>--%>
    <%--<br/>--%>
<%--</sec:authorize>--%>

<%--<sec:authorize access="isAuthenticated() and principal.username='u'">--%>
    <%--<a href="/admin">Animistration</a>--%>
<%--</sec:authorize>--%>


<%--principal.username : <sec:authentication property="principal.username"/>--%>
<%--<br/>--%>
<%--principal.password : <sec:authentication property="principal.password"/>--%>
<%--<br/>--%>
<%--principal.email : <sec:authentication property="principal.email"/>--%>
<%--<br/>--%>
<%--principal.enabled : <sec:authentication property="principal.enabled"/>--%>
<%--<br/>--%>
<%--principal.accountNonExpired : <sec:authentication property="principal.accountNonExpired"/>--%>
<br/>

</body>
</html>
