<%@ page import="com.costsmanagerhit.model.User" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <%
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
        }
    %>
    <title>User Logged Successfully</title>
</head>
<body>
    <div style="text-align: center;">
        Welcome <%=Objects.requireNonNull(user).getUsername()%>, you have logged in!
    </div>
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
