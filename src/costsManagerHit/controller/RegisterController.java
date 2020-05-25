package costsManagerHit.controller;

import costsManagerHit.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterController {

    public void register(HttpServletRequest request, HttpServletResponse response, String data) {
        System.out.println("register in register controller");
//		Map<Integer,Product> products = ProductsDAO.createInstance().getProducts();
//		request.setAttribute("products", "test1");
    }

    public boolean attemptRegister(HttpServletRequest request, HttpServletResponse response, String data) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        User user = new User(userName, password);

        try {
            IUserDAO iUserDAOHibernate = UserDAOHibernate.getInstance();
//            TODO check if DB exists first
            if (iUserDAOHibernate.userExistsInDb(userName))
                return false;
            else
                iUserDAOHibernate.addUser(user);
        } catch (UserDAOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

}
