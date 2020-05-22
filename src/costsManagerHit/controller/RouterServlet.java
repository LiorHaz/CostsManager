// URL for example: http://localhost:8010/CostsManagerHit/expenses

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
import java.util.Objects;


/**
 * Servlet implementation class RouterServlet
 */
@WebServlet(urlPatterns = {"/CostsManagerHit/*"})
public class RouterServlet extends HttpServlet {

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
			String id = null;
			String controllerName = null;

			if (urlArray.length > 2)
				controllerName = urlArray[2];
			if (urlArray.length > 3)
				action = urlArray[3];
			else
				action = controllerName;
			if (urlArray.length > 4)
				id = urlArray[4];
//			TODO check if controllerName is null and route to pageNotFound

			String controllerClassFullPath = getControllerClassFullPath(controllerName);

			Class<?> myController = Class.forName(controllerClassFullPath);
			method = myController.getMethod(action, HttpServletRequest.class, HttpServletResponse.class, String.class);
			method.invoke(myController.newInstance(), request, response, id);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+action+".jsp");
			dispatcher.include(request,response);

		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException |	InvocationTargetException
				| InstantiationException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	private String getControllerClassFullPath(String controllerName) {
		String fullControllerName = controllerName + "Controller";
		return costsManagerHit.config.CONTROLLERS_PACKAGE + "." + fullControllerName.substring(0, 1).toUpperCase() +
				fullControllerName.substring(1);
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
