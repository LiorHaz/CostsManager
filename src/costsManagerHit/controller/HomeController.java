package costsManagerHit.controller;

import costsManagerHit.model.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

public class HomeController {

    public boolean home(HttpServletRequest request, HttpServletResponse response, String data) {
        //        TODO pull expense id from DB by user not by allExpenses
        Expense[] lastThreeExpenses;
        try {
            User user = (User) request.getSession(false).getAttribute("user");
            Expense[] allExpenses = ExpenseDAOHibernate.getInstance().getUserExpenses(user.getId());
            lastThreeExpenses = splitFirstThreeElements(allExpenses);
            request.setAttribute("expenses", lastThreeExpenses);
        } catch (ExpenseDAOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Expense[] splitFirstThreeElements(Expense[] allExpenses)
    {
        Expense[] firstThreeElements;
        if (allExpenses == null)
            firstThreeElements = new Expense[0];
        else {
            if (allExpenses.length < 3)
                firstThreeElements = Arrays.copyOfRange(allExpenses, 0, allExpenses.length);
            else
                firstThreeElements = Arrays.copyOfRange(allExpenses, 0, 3);
        }
        return firstThreeElements;
    }

    private boolean appCookieExists(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("costsManager"))
                return true;
        }
        return false;
    }

    public boolean addExpense(HttpServletRequest request, HttpServletResponse response, String data) {
        User user = (User) request.getSession(false).getAttribute("user");
        String type = request.getParameter("expenseType");
        String month = request.getParameter("expenseMonth");
        String description = request.getParameter("expenseDescription");
        double amount = Double.parseDouble(request.getParameter("expenseAmount"));
        Expense expense = new Expense(amount, type, description, month, user.getId());
        try {
            IExpenseDAO iExpenseDAOHibernate = ExpenseDAOHibernate.getInstance();
            iExpenseDAOHibernate.addExpense(expense);
            response.sendRedirect("http://localhost:8010/CostsManagerHit/home");
            return false;
        } catch (ExpenseDAOException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
