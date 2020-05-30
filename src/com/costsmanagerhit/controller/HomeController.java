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

public class HomeController {
    /**
     * Action for home view, setting last three expenses attribute
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     */
    public boolean home(HttpServletRequest request, HttpServletResponse response, String data) {
        Expense[] lastThreeExpenses;
        try {
            User user=(User)request.getSession().getAttribute("user");
            if(user == null){
                response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
                return true;
            }
            Expense[] allExpenses = ExpenseDAOHibernate.getInstance().getUserExpenses(user.getId());
            lastThreeExpenses = splitFirstThreeElements(allExpenses);
            request.setAttribute("expenses", lastThreeExpenses);
        } catch (ExpenseDAOException | IOException e) {
            e.printStackTrace();
        }
        return false;
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
