<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="requestURI" type="java.lang.String" scope="request"/>
<html>
<head>
    <title>Login page</title>
</head>
<body>

<form method="POST" action="${requestURI}">
<%--<form method="POST" action="/j_security_check">--%>
    <input type="text" name="j_username" title="login">
    <input type="password" name="j_password" autocomplete="off" title="password">
    <input type="submit" value="submit">
</form>

</body>
</html>
