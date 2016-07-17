<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Gun" %>
<jsp:useBean id="guns" type="java.util.Collection<model.Gun>" scope="request"/>
<html>
<head>
    <title>Welcome to our gun shop!</title>
</head>
<body>

    <table>
        <tr>
            <th>Название</th>
            <th>Калибр</th>
        </tr>
        <% for (Gun gun: guns) {%>
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
