package costsManagerHit.controller;

import costsManagerHit.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController {

    public void login(HttpServletRequest request, HttpServletResponse response, String data) {
        System.out.println("login in login controller");
    }

    public boolean attemptLogin(HttpServletRequest request, HttpServletResponse response, String data) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        try {
            IUserDAO iUserDAOHibernate = UserDAOHibernate.getInstance();
            User user=iUserDAOHibernate.validateUserAndPassword(userName, password);
            //The user logged in successfully
            if (user!=null)
            {   //set reference of the current user for this session
                request.getSession().setAttribute("user",user);
                ExpensesController.expenses(request, response, data);
                return true;
            }
            else //An indicator for unsuccessful login
                request.setAttribute("isSuccessfullyLoggedIn",false);
        } catch (ExpenseDAOException | UserDAOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void logOut(HttpServletRequest request, HttpServletResponse response, String data) {
        System.out.println("logOut in login controller");
    }

}