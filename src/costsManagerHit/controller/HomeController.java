package costsManagerHit.controller;

import costsManagerHit.model.Expense;
import costsManagerHit.model.ExpenseDAOException;
import costsManagerHit.model.ExpenseDAOHibernate;
import costsManagerHit.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

public class HomeController {

    public void home(HttpServletRequest request, HttpServletResponse response, String data) {
        Expense[] lastThreeExpenses;
        try {
            User user=(User)request.getSession().getAttribute("user");
            Expense[] allExpenses = ExpenseDAOHibernate.getInstance().getUserExpenses(user.getId());
            lastThreeExpenses = splitFirstThreeElements(allExpenses);
            request.setAttribute("expenses", lastThreeExpenses);
        } catch (ExpenseDAOException e) {
            e.printStackTrace();
        }
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

}
