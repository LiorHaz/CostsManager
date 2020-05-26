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
