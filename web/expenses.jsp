<%@ page import="costsManagerHit.model.Expense" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="/css/common.css">
  <link rel="stylesheet" type="text/css" href="/css/expenses.css">
  <title>Main Page</title>
</head>

<body>
<%--<form method="post" action="http://localhost:8010/CostsManagerHit/expenses">
  <input type="submit" value="Get All"/>--%>
</form>
<h3>Filter by month</h3>
<form method="post" action="http://localhost:8010/CostsManagerHit/expenses/filterByMonth">
  <label for="filteredMonth">Select month:</label>
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

<h3>Filter by search</h3>
<form method="post" action="http://localhost:8010/CostsManagerHit/expenses/filterBySearch">
  <label for="expenseType">Choose a type:</label>
  <select id="expenseType" name="expenseType">
    <option value="general">General</option>
    <option value="car">Car</option>
    <option value="house">House</option>
    <option value="food">Food</option>
  </select>
  <br> <br>
  <label for="expenseMonth">Choose a month: </label>
  <select id="expenseMonth" name="expenseMonth">
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
  Minimum amount: <input  type="number" step="0.01" name="expenseMinAmount" style="width: 7em;" required />
  &nbsp;&nbsp;&nbsp;
  Maximum amount: <input  type="number" step="0.01" name="expenseMaxAmount" style="width: 7em;" required/>
  <br> <br>
  <input type="submit" value="Search"/>
</form>

<%
  if(request.getAttribute("addedSuccessfully")!=null){
    out.println("Expense was added successfully!");
    request.removeAttribute("addedSuccessfully");
  }

  Boolean areAllExpenses=(Boolean)request.getAttribute("allExpenses");
  if(areAllExpenses!=null) {
    if (areAllExpenses)
      out.print("<h2>Your Expenses:</h2>");
    else
      out.print("<h2>Your Last Three Expenses:</h2>");
  }
  else {
    Boolean isAddedSuccessfully=(Boolean)request.getAttribute("addedSuccessfully");
    if(isAddedSuccessfully!=null){
      if(isAddedSuccessfully)
        out.print("<h2>Your Last Three Expenses:</h2>");
    }
    else
      out.print("<h2>Your Expenses:</h2>");
  }
  Cookie[] cookies = request.getCookies();

  Expense[] expenses = (Expense[])(request.getAttribute("expenses"));
  if(expenses!=null){
    out.print("<table>\n" +
            "  <tr>\n" +
            "    <td>Type</td>\n" +
            "    <td>Price</td>\n" +
            "    <td>Description</td>\n" +
            "    <td>Month</td>\n" +
            "  </tr>");
    for (Expense currentExpense : expenses)
    {
      String type = currentExpense.getType();
      double amount = currentExpense.getAmount();
      String description = currentExpense.getDescription();
      String month = currentExpense.getMonth();
%>
<tr>
  <td><%= type %></td>
  <td><%= String.valueOf(amount) %></td>
  <td><%= description %></td>
  <td><%= month %></td>
</tr>
<%
    }
    out.print("</table>");
  }
  else
    out.println("There are no expenses which match to your search");

  if(request.getAttribute("month")!=null) {
    out.print("<br> <br>");
    String month = (String) request.getAttribute("month");
    if (expenses.length == 0)
      out.print("There are no Expenses for " + month);
    else {
      double sum=(double)request.getAttribute("sum");
      out.println("Total for " + month + ": " +(Math.round(sum * 10) / 10.0)+" $");
    }
  }
%>
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
