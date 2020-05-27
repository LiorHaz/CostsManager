package costsManagerHit.controller;

import costsManagerHit.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController {

    public boolean login(HttpServletRequest request, HttpServletResponse response, String data) {
        return true;
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
                response.sendRedirect("http://localhost:8010/CostsManagerHit/login/userLogged");
//                TODO pass authentication using cookies
//                String forwardUrl = "/CostsManagerHit/home/home/" + user.getId();
//                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(forwardUrl);
//                dispatcher.forward(request, response);
                return false;
            }
        } catch (UserDAOException | IOException e) {
            e.printStackTrace();
        }
        request.setAttribute("isSuccessfullyLoggedIn",false);
        return true;
    }

    private void createLoginCookie(HttpServletResponse response) {
        Cookie appCookie = new Cookie("costsManager", "1");
        appCookie.setMaxAge(99999);
        response.addCookie(appCookie);
    }

    public boolean logOut(HttpServletRequest request, HttpServletResponse response, String data) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
        return false;
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

    public boolean userLogged(HttpServletRequest request, HttpServletResponse response, String data) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/userLogged.jsp");
        dispatcher.include(request,response);
        return false;
    }

}