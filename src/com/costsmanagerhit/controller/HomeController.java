package com.costsmanagerhit.controller;

import com.costsmanagerhit.model.ExpenseDAOException;
import com.costsmanagerhit.model.ExpenseDAOHibernate;
import com.costsmanagerhit.model.User;
import com.costsmanagerhit.model.Expense;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Represents the home controller, which connects and passes data between the jsp pages and the model objects
 */
public class HomeController {

    /**
     * Action for home view, setting last three expenses attribute
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     */
    public boolean home(HttpServletRequest request, HttpServletResponse response, String data) {
        try {
            User user=(User)request.getSession().getAttribute("user");
            if(user == null){
                response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
                return true;
            }
            else
                setLastThreeUserExpensesAttribute(user, request);
        } catch (ExpenseDAOException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Set "expenses" in the session with last three user expenses
     * @param user The logged in user
     * @param request The request sent from client
     */
    private void setLastThreeUserExpensesAttribute(User user, HttpServletRequest request) throws ExpenseDAOException {
        Expense[] allExpenses = ExpenseDAOHibernate.getInstance().getUserExpenses(user.getId());
        Expense[] lastThreeExpenses = splitFirstThreeElements(allExpenses);
        request.setAttribute("expenses", lastThreeExpenses);
    }

    /**
     * Takes an array and return a new array made of the first 3 values
     * @param allExpenses The expenses array which will be split
     * @return The last three expenses as array
     */
    private Expense[] splitFirstThreeElements(Expense[] allExpenses) {
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

    /**
     * Check if the app cookie exists in the client cookies list
     * @param request The request which was sent to the controller
     * @return true if the cookie exist - false otherwise
     */
    private boolean appCookieExists(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("costsManager"))
                return true;
        }
        return false;
    }

}
