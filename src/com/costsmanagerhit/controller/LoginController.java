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

public class LoginController {
    /**
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     * @return boolean returns if redirect was sent
     */
    public boolean login(HttpServletRequest request, HttpServletResponse response, String data){
//        TODO if user already login redirect to home
        return false;
    }

    /**
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
                response.sendRedirect("http://localhost:8010/CostsManagerHit/login/userLogged");
                return true;
            }
            else
                request.setAttribute("isSuccessfullyLoggedIn",false);
        } catch (UserDAOException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     */
    public boolean logOut(HttpServletRequest request, HttpServletResponse response, String data) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
        return false;
    }

    /**
     * @param response The response which was sent to the controller
     */
    private void createLoginCookie(HttpServletResponse response) {
        Cookie appCookie = new Cookie("costsManager", "1");
        appCookie.setMaxAge(99999);
        response.addCookie(appCookie);
    }

    /**
     * @param request The request which was sent to the controller
     * @return true if the cookie exist - false otherwise
     */
    private boolean appCookieExists(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("costsManager"))
                    return true;
            }
        }
        return false;
    }

    /**
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     */
    public boolean userLogged(HttpServletRequest request, HttpServletResponse response, String data) throws ServletException, IOException {
//        TODO if user login already redirect to home
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/userLogged.jsp");
        dispatcher.include(request,response);
        return true;
    }
}