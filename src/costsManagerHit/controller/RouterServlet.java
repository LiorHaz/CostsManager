// URL for example: http://localhost:8010/CostsManagerHit/Costs

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
			String text = request.getRequestURI();

			// extracting the controller name
			String[] texts = text.split("/");

			// extracting controller and action
			String action = null;
			String id = null;

			String controller = texts[2];
			if (texts.length > 3)
				action = texts[3];
			if (texts.length > 4)
				id = texts[4];

			// building the full qualified name of the controller
			String temp = controller + "Controller";

			String controllerClassName = costsManagerHit.config.CONTROLLERS_PACKAGE + "."
					+ temp.substring(0, 1).toUpperCase() + temp.substring(1);

			System.out.println(controllerClassName);
//			TODO keep working on the routes

//			// instantiating the controller class and calling the action method on the controller object
//			Class myController = Class.forName(controllerClassName);
//			Method method;
//			if (action.equals("store")) {
//				method = myController.getMethod("products", HttpServletRequest.class, HttpServletResponse.class, String.class);
//			}
//			else {
//				method = myController.getMethod(action, HttpServletRequest.class, HttpServletResponse.class, String.class);
//			}
//			method.invoke(myController.newInstance(), request, response, id);
//
//			// creating a RequestDispatcher object that points at the JSP document which is view of our action
//			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+action+".jsp");
//			dispatcher.include(request,response);

		} catch ( IllegalArgumentException |	SecurityException e) {
			e.printStackTrace();
		}
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
