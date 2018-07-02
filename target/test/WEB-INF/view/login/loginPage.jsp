<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2018. 6. 30.
  Time: 오후 2:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Login Page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

</head><body onload='document.f.username.focus();'>

<jsp:include page="/WEB-INF/view/header/header.jsp"/>

<h3>Login with Username and Password</h3>

    <%--<form name='f' action='/login' method='POST'>--%>
    <form id="login" name='f' action='/login/login' method='POST'>
    <table>


        <tr><td>User:</td><td><input type='text' name='username' value=''></td></tr>
        <tr><td>Password:</td><td><input type='password' name='password'/></td></tr>
        <tr>
            <td><label for="remember_me" class="inline">
                <input id="remember_me" name="remember_me" type="checkbox">
            Remeber me</label></td>
        </tr>
        <input type='hidden' id='signature' name='signature' value="asjdlkfjaksdf"/>

        <tr><td colspan='2'><input name="submit" type="submit" value="Login"/></td></tr>
    </table>
</form>
</body></html>