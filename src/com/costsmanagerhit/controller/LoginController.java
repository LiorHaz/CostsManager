package com.costsmanagerhit.controller;

import com.costsmanagerhit.model.IUserDAO;
import com.costsmanagerhit.model.User;
import com.costsmanagerhit.model.UserDAOException;
import com.costsmanagerhit.model.UserDAOHibernate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents the login controller, which connects and passes data between the jsp pages and the model objects
 */
public class LoginController {

    /**
     * Redirect to home URL if user is already logged in
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     * @return boolean returns if redirect was sent
     */
    public boolean login(HttpServletRequest request, HttpServletResponse response, String data) {
        String appCookieValue = getAppCookieValue(request);
        if (!Objects.equals(appCookieValue, "")) {
            String[] cookieValues = appCookieValue.split("_");
            String userName = cookieValues[0];
            String password = cookieValues[1];
            return loginUsingCookie(request, response, userName, password);
        }
        else {
            User user = (User)request.getSession().getAttribute("user");
            if(user != null){
                try {
                    response.sendRedirect("http://localhost:8010/CostsManagerHit/home");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Verify user cookie information with the DB, if it fits, saves the user into the session and redirect to home
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param userName user name taken from cookie
     * @param password password taken from cookie
     * @return boolean returns if redirect was sent
     */
    private boolean loginUsingCookie(HttpServletRequest request, HttpServletResponse response, String userName, String password) {
        IUserDAO iUserDAOHibernate = UserDAOHibernate.getInstance();
        try {
            User user = iUserDAOHibernate.validateUserAndPassword(userName, password);
            if (user != null)
            {
                request.getSession().setAttribute("user", user);
                response.sendRedirect("http://localhost:8010/CostsManagerHit/login/userLogged");
            }
            else{
                removeAppCookie(response);
                response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
            }
        } catch (UserDAOException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Verify user form information with the DB, if it fits, saves the user into the session and redirect to home
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     * @return boolean returns if redirect was sent
     */
    public boolean attemptLogin(HttpServletRequest request, HttpServletResponse response, String data) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        try {
            IUserDAO iUserDAOHibernate = UserDAOHibernate.getInstance();
            User user = iUserDAOHibernate.validateUserAndPassword(userName, password);
            if (user != null)
            {
                request.getSession().setAttribute("user", user);
                createLoginCookie(response, userName, password);
                response.sendRedirect("http://localhost:8010/CostsManagerHit/login/userLogged");
                return true;
            }
            else
                request.setAttribute("isSuccessfullyLoggedIn",false);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UserDAOException e) {
            System.out.println(e.getMessage());
            request.setAttribute("isSuccessfullyLoggedIn",false);
            return false;
        }
        return false;
    }

    /**
     * Remove the user session/cookie information and redirect to login
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     * @return true since redirect always happen in logOut
     */
    public boolean logOut(HttpServletRequest request, HttpServletResponse response, String data) {
        request.getSession().removeAttribute("user");
        removeAppCookie(response);
        try {
            response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Remove the app cookie in the user brewser
     * @param response The response which was sent to the controller
     */
    private void removeAppCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("costsManager", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * Create a cookie for the page (need to ask if we need to use this or not)
     * @param response The response which was sent to the controller
     */
    private void createLoginCookie(HttpServletResponse response, String userName, String password) {
        Cookie appCookie = new Cookie("costsManager", userName + "_" + password);
        appCookie.setMaxAge(99999);
        response.addCookie(appCookie);
    }

    /**
     * Return app cookie value if it exists, if not return an empty string
     * @param request The request which was sent to the controller
     * @return The application cookie value
     */
    private String getAppCookieValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("costsManager"))
                    return cookie.getValue();
            }
        }
        return "";
    }

    /**
     * Method for redirecting to "userLogged" after successful login action
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     * @return true since there's no need to load jsp in router
     */
    public boolean userLogged(HttpServletRequest request, HttpServletResponse response, String data) {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/userLogged.jsp");
        try {
            dispatcher.include(request,response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}