<%@ page import="costsManagerHit.model.Expense" %>
<%@ page import="costsManagerHit.model.User" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="/css/common.css">
  <link rel="stylesheet" type="text/css" href="/css/expenses.css">
  <title>Main Page</title>
</head>

<body>
  <h2>Last Three Expenses:</h2>
  <table>
    <tr>
      <td>Expense ID</td>
      <td>Type</td>
      <td>Price</td>
      <td>Description</td>
      <td>Month</td>
    </tr>

    <%
      Cookie[] cookies = request.getCookies();

      Expense[] expenses = (Expense[])(request.getAttribute("expenses"));

      for (Expense currentExpense : expenses)
      {
        int id = currentExpense.getId();
        String type = currentExpense.getType();
        double amount = currentExpense.getAmount();
        String description = currentExpense.getDescription();
        String month = currentExpense.getMonth();
    %>
    <tr>
      <td><%= String.valueOf(id) %></td>
      <td><%= type %></td>
      <td><%= String.valueOf(amount) %></td>
      <td><%= description %></td>
      <td><%= month %></td>
    </tr>
    <%
      }
    %>
  </table>
  <br> <br>
  <form method="post" action="http://localhost:8010/CostsManagerHit/expenses/filterByMonth">
    <label for="filteredMonth">Filter by month: </label>
    <select id="filteredMonth" name="filteredMonth">
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
    <input type="submit" value="Filter"/>
  </form>
  <form method="post" action="http://localhost:8010/CostsManagerHit/expenses">
    <input type="submit" value="Get All"/>
  </form>
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
