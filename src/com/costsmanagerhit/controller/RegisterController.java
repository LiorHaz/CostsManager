package com.costsmanagerhit.controller;

import com.costsmanagerhit.model.IUserDAO;
import com.costsmanagerhit.model.User;
import com.costsmanagerhit.model.UserDAOException;
import com.costsmanagerhit.model.UserDAOHibernate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterController {
    /**
     * Starting function for register URL
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     * @return false - no redirect is sent
     */
    public boolean register(HttpServletRequest request, HttpServletResponse response, String data) {
        return false;
    }

    /**
     * Attempt to register the user information sent in the form
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     * @return boolean returns if redirect was sent
     */
    public boolean attemptRegister(HttpServletRequest request, HttpServletResponse response, String data) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        try {
            IUserDAO iUserDAOHibernate = UserDAOHibernate.getInstance();
            User user = iUserDAOHibernate.addUser(userName,password);
            response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
            request.setAttribute("isRegisteredSuccessfully",true);
        } catch (UserDAOException e) {
            System.out.println(e.getMessage());
            request.setAttribute("isRegisteredSuccessfully",false);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Check if the user object is null or not
     * @param user the user returned from addUser function
     * @return boolean if user is null or not
     */
    private boolean userAlreadyExistsInDb(User user) {
        return user == null;
    }
}
