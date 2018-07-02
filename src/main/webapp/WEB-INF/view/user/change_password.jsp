<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/view/header/header.jsp"/>

<h3>비밀번호 수정</h3>

<form action="/user/change_password" method="post">
    <label for="old_password"><input type="text" id="old_password" name="old_password" value=""></label>
    <label for="new_password"><input type="text" id="new_password" name="new_password" value=""></label>
    <input type="submit" value="비밀번호 변경">
</form>

</body>
</html>
</title>
</head>
<body>

</body>
</html>
