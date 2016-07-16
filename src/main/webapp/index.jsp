<%@ page contentType="text/html;charset=UTF-8" language="java"
    import="java.util.*" %>
<%@ page import="model.Gun" %>
<jsp:useBean id="guns" class="java.util.HashSet" scope="request"/>
<html>
<head>
    <title>Welcome to our gun shop!</title>
</head>
<body>

    Hello, World!

    <table>
        <tr>
            <th>Название</th>
            <th>Калибр</th>
        </tr>
        <%
            for (Gun gun: (Collection<Gun>) guns) {%>
                <tr>
                    <td><%=gun.getName()%></td>
                    <td><%=gun.getCaliber()%></td>
                </tr>
        <%
            }
        %>
    </table>

</body>
</html>
