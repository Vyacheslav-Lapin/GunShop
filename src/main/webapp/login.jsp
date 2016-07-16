<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 16/07/16
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>

<form method="POST" action="/j_security_check">
    <input type="text" name="j_username">
    <input type="password" name="j_password" autocomplete="off">
    <input type="submit" value="submit">
</form>

</body>
</html>
