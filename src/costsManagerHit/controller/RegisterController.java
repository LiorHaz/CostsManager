package costsManagerHit.controller;

import costsManagerHit.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterController {

    public boolean register(HttpServletRequest request, HttpServletResponse response, String data) {
        return true;
    }

    public boolean attemptRegister(HttpServletRequest request, HttpServletResponse response, String data) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        try {
            IUserDAO iUserDAOHibernate = UserDAOHibernate.getInstance();
            User user=iUserDAOHibernate.addUser(userName,password);
            if (user==null) {//The user already exists in db
                //Getting an indicator for register failed/done msg
                request.setAttribute("isRegisteredSuccessfully",false);
                return false;
            }
            else { //The user doesn't exist - add the user to db and set its reference to the session
                //Getting an indicator for register failed/done msg
                request.setAttribute("isRegisteredSuccessfully",true);
            }
        } catch (UserDAOException  e) {
            e.printStackTrace();
        }
        return true;
    }

}
