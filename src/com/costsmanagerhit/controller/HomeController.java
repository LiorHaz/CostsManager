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
     *
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     */
    public void home(HttpServletRequest request, HttpServletResponse response, String data) {
        Expense[] lastThreeExpenses;
        try {
            User user=(User)request.getSession().getAttribute("user");
            if(user==null){
                goToLogin(request, response);
                return;
            }
            Expense[] allExpenses = ExpenseDAOHibernate.getInstance().getUserExpenses(user.getId());
            lastThreeExpenses = splitFirstThreeElements(allExpenses);
            request.setAttribute("expenses", lastThreeExpenses);
        } catch (ExpenseDAOException e) {
            e.printStackTrace();
        }
    }
    /**
     *
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
     *
     * @param request The request which was sent to the controller
     * @param response  The response which was sent to the controller
     *
     */
    private void goToLogin(HttpServletRequest request, HttpServletResponse response){
        String forwardUrl = "/CostsManagerHit/login";
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(forwardUrl);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *
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
