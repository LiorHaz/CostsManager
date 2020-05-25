<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link rel="stylesheet" type="text/css" href="/css/register.css">
    <title>Register</title>
</head>

<body>
<form method="post" action="http://localhost:8010/CostsManagerHit/register/attemptRegister">
    Please fill the form in order to register:
    <br> <br>
    User Name: <input type="text" name="userName" />
    <br> <br>
    Password: <input type="text" name="password" />
    <br> <br>
    <input type="submit" value="Register"/>
</form>
</body>

<footer>
    <nav>
        <ul>
            <li>
                <a href="http://localhost:8010/CostsManagerHit/expenses">Expenses</a>
            </li>
            <li>
                <a href="http://localhost:8010/CostsManagerHit/login">Login</a>
            </li>
            <li>
                <a href="http://localhost:8010/CostsManagerHit/register">Register</a>
            </li>
        </ul>
    </nav>
</footer>
</html>
