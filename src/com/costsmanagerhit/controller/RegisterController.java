package com.costsmanagerhit.controller;

import com.costsmanagerhit.model.IUserDAO;
import com.costsmanagerhit.model.User;
import com.costsmanagerhit.model.UserDAOException;
import com.costsmanagerhit.model.UserDAOHibernate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterController {
    /**
     *
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     * @return true with no reason - just redirecting to login page
     */
    public boolean register(HttpServletRequest request, HttpServletResponse response, String data) {
        return true;
    }
    /**
     *
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     * @return true if registration succeeded, otherwise - false
     */
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
            else { //The user doesn't exist - add the user to db
                //Getting an indicator for register failed/done msg
                request.setAttribute("isRegisteredSuccessfully",true);
            }
        } catch (UserDAOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
