<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2018. 6. 25.
  Time: 오후 9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

<h3>사용자 수정</h3>

<form action="/user/edit" method="post">
    <label for="user_no"><input type="text" id="user_no" value="${user.userNo}"></label>
    <input type="submit" value="확인">
</form>

</body>
</html>
