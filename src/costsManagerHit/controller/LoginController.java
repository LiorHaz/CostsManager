package costsManagerHit.controller;

import costsManagerHit.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController {

    public void login(HttpServletRequest request, HttpServletResponse response, String data) {
    }

    public boolean attemptLogin(HttpServletRequest request, HttpServletResponse response, String data) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        try {
            IUserDAO iUserDAOHibernate = UserDAOHibernate.getInstance();
            User user = iUserDAOHibernate.validateUserAndPassword(userName, password);
            if (user != null)
            {
                request.getSession().setAttribute("user", user);
                String forwardUrl = "/CostsManagerHit/home/home/" + user.getId();
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(forwardUrl);
                dispatcher.forward(request, response);
                return true;
            }
            else
                request.setAttribute("isSuccessfullyLoggedIn",false);
        } catch (UserDAOException | IOException | ServletException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createLoginCookie(HttpServletResponse response) {
        Cookie appCookie = new Cookie("costsManager", "1");
        appCookie.setMaxAge(99999);
        response.addCookie(appCookie);
    }

    public void logOut(HttpServletRequest request, HttpServletResponse response, String data) {
       request.getSession().setAttribute("user",null);
    }

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

}