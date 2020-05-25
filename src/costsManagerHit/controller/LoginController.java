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
            if (iUserDAOHibernate.nameAndPassMatchDb(userName, password))
            {
//                TODO change to "getExpensesById"
                ExpensesController.expenses(request, response, data);
                return true;
            }
        } catch (ExpenseDAOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void logOut(HttpServletRequest request, HttpServletResponse response, String data) {
        System.out.println("logOut in login controller");
    }

}
