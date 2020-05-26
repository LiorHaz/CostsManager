package costsManagerHit.controller;

import costsManagerHit.model.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController {

    public void login(HttpServletRequest request, HttpServletResponse response, String data) throws IOException {
        if (appCookieExists(request))
        {
            response.sendRedirect("http://localhost:8010/CostsManagerHit/home");
        }
    }

    public boolean attemptLogin(HttpServletRequest request, HttpServletResponse response, String data) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        try {
            IUserDAO iUserDAOHibernate = UserDAOHibernate.getInstance();
            User user = iUserDAOHibernate.validateUserAndPassword(userName, password);
            if (user != null)
            {
//                TODO insert into cookie the user ID fix the cookie issue
                Cookie appCookie = new Cookie("costsManager", "1");
                appCookie.setMaxAge(99999);
                response.addCookie(appCookie);
                request.getSession().setAttribute("user",user);
                response.sendRedirect("http://localhost:8010/CostsManagerHit/home");
                return true;
            }
            else
                request.setAttribute("isSuccessfullyLoggedIn",false);
        } catch (UserDAOException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void logOut(HttpServletRequest request, HttpServletResponse response, String data) {
       request.getSession().setAttribute("user",null);
    }

    private boolean appCookieExists(HttpServletRequest request) {
        Cookie cookies[]= request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName() == "costsManager")
                    return true;
            }
        }
        return false;
    }

}