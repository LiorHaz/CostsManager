// URL for example: http://localhost:8010/CostsManagerHit/home

package costsManagerHit.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;


/**
 * Servlet implementation class RouterServlet
 */
@WebServlet(urlPatterns = {"/CostsManagerHit/*"})
public class RouterServlet extends HttpServlet {

	String[] existingControllersNames = {"Expenses", "Login", "Register", "PageNotFound", "Home"};

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RouterServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			String[] urlArray = request.getRequestURI().split("/");

			Method method;
			String action;
			String id = "";
			String viewName = "";

			if (urlArray.length > 2)
				viewName = urlArray[2];
				if (Objects.equals(viewName, "css"))
					return;
			String controllerName = urlArray[2].substring(0, 1).toUpperCase() + urlArray[2].substring(1);
			if (urlArray.length > 3)
				action = urlArray[3].substring(0, 1).toLowerCase() + urlArray[3].substring(1);
			else
				action = controllerName.substring(0, 1).toLowerCase() + controllerName.substring(1);
			if (urlArray.length > 4)
				id = urlArray[4];

			if (Arrays.stream(existingControllersNames).noneMatch(controllerName::equals))
			{
				controllerName = "PageNotFound";
				action = "pageNotFound";
			}

			String controllerClassFullPath = getControllerClassFullPath(controllerName);

			Class<?> myController = Class.forName(controllerClassFullPath);
			method = myController.getMethod(action, HttpServletRequest.class, HttpServletResponse.class, String.class);
			Boolean actionReturnValue = (Boolean) method.invoke(myController.newInstance(), request, response, id);

			if(Objects.equals(action, "attemptRegister") && actionReturnValue)
				viewName="login";

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+viewName+".jsp");
			dispatcher.include(request,response);

		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| InstantiationException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	private String getControllerClassFullPath(String controllerName) {
		return costsManagerHit.config.CONTROLLERS_PACKAGE + "." + controllerName + "Controller";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
