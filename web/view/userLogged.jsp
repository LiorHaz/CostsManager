<%@ page import="com.costsmanagerhit.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
        }
    %>
    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <title>User Logged Successfully</title>
</head>
<body>
    <center>
        Welcome, <%=user.getUsername()%>
    </center>
</body>

<footer>
    <nav>
        <ul>
            <li>
                <a href="http://localhost:8010/CostsManagerHit/home">Add Expense</a>
            </li>
            <li>
                <a href="http://localhost:8010/CostsManagerHit/expenses">Show All Expenses</a>
            </li>
            <li>
                <a href="http://localhost:8010/CostsManagerHit/login/logOut">Log Out</a>
            </li>
        </ul>
    </nav>
</footer>
</html>
