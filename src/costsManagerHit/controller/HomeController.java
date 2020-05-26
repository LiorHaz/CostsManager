package costsManagerHit.controller;

import costsManagerHit.model.ExpenseDAOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static costsManagerHit.controller.ExpensesController.setAttributeLastThreeExpenses;

public class HomeController {

    public void home(HttpServletRequest request, HttpServletResponse response, String data) {
        try {
            setAttributeLastThreeExpenses(request, response, data);
        } catch (ExpenseDAOException e) {
            e.printStackTrace();
        }
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
