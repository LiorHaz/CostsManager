<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link rel="stylesheet" type="text/css" href="/css/register.css">
    <title>Register</title>
</head>

<body>
<%--TODO add "register failed msg" or "register done msg" using getParameter--%>
<form method="post" action="http://localhost:8010/CostsManagerHit/register/attemptRegister">
    Please fill the form in order to register:
    <br> <br>
    User Name: <input type="text" name="userName" required/>
    <br> <br>
    Password: <input type="password" name="password" required/>
    <br> <br>
    <input type="submit" value="Register"/>
</form>
<%
Boolean isSuccessful= (Boolean)request.getAttribute("isRegisteredSuccessfully");
if(isSuccessful!=null)
    if(!isSuccessful)
        out.println("This username is already taken.</br>Please try another username.");
%>
</body>

<footer>
    <nav>
        <ul>
            <li>
                <a href="http://localhost:8010/CostsManagerHit/login">Login</a>
            </li>
        </ul>
    </nav>
</footer>
</html>
