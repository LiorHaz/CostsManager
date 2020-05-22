<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="/css/common.css">
  <link rel="stylesheet" type="text/css" href="/css/expenses.css">
  <title>Main Page</title>
</head>

<body>
  <h2>Add an Expense</h2>
  <form method="post" action="http://localhost:8010/CostsManagerHit/expenses/addExpense">
    Choose a type: <select id="expenseType" name="expenseType">
      <option value="general">general</option>
      <option value="car">car</option>
      <option value="house">house</option>
      <option value="food">food</option>
    </select>
    <br> <br>
    Choose a month: <select id="expenseMonth" name="expenseMonth">
      <option value="January">January</option>
      <option value="February">February</option>
      <option value="March">March</option>
      <option value="April">April</option>
      <option value="May">May</option>
      <option value="June">June</option>
      <option value="July">July</option>
      <option value="August">August</option>
      <option value="September">September</option>
      <option value="October">October</option>
      <option value="November">November</option>
      <option value="December">December</option>
    </select>
    <br> <br>
    Description: <input type="text" name="expenseDescription" />
    <br> <br>
    Amount: <input type="number" name="expenseAmount" />
    <br> <br>
    <input type="submit" value="Save"/>
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
