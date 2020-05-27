<%@ page import="com.sun.org.apache.xpath.internal.operations.Bool" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link rel="stylesheet" type="text/css" href="/css/login.css">
    <title>Log In</title>
</head>

<body>
    <form method="post" action="http://localhost:8010/CostsManagerHit/login/attemptLogin">
        Welcome stranger, please log in first:
        <br> <br>
        User Name: <input type="text" name="userName" required/>
        <br> <br>
        Password: <input type="password" name="password" required/>
        <br> <br>
        <input type="submit" value="Log In"/>
    </form>
<%
    Boolean isSuccessful=(Boolean)request.getAttribute("isSuccessfullyLoggedIn");
    if(isSuccessful!=null)
        if(!isSuccessful)
            out.println("Invalid username or password.</br>Please try again.");
    Boolean isRegisteredSuccessfully=(Boolean)request.getAttribute("isRegisteredSuccessfully");
    if(isRegisteredSuccessfully!=null)
        if(isRegisteredSuccessfully) {
            out.println("Registration succeeded.");
            request.removeAttribute("isRegisteredSuccessfully");
        }
%>
</body>

<footer>
    <nav>
        <ul>
            <li>
                <a href="http://localhost:8010/CostsManagerHit/register">Register</a>
            </li>
        </ul>
    </nav>
</footer>
</html>
