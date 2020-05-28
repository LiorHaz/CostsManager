// URL for example: http://localhost:8010/CostsManagerHit/login

package com.costsmanagerhit.controller;

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

//TODO:Clean and organize the code in the servlet Roni!
/**
 * Servlet implementation class RouterServlet
 */
@WebServlet(urlPatterns = {"/CostsManagerHit/*"})
public class RouterServlet extends HttpServlet {

	final String[] existingControllersNames = {"Expenses", "Login", "Register", "PageNotFound", "Home"};
	String controllerName = "";
	String action = "";
	String id = "";
	String viewName = "";

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
			Method method;

			response.setContentType("text/html");
			String[] urlArray = request.getRequestURI().split("/");

			if (urlPointToCssFile(urlArray))
				return;

			getVariablesValuesFromUrl(urlArray);
			if (controllerDoesntExists(controllerName))
			{
				controllerName = "PageNotFound";
				action = "pageNotFound";
			}

			String controllerClassFullPath = getControllerClassFullPath(controllerName);

			Class<?> myController = Class.forName(controllerClassFullPath);
			method = myController.getMethod(action, HttpServletRequest.class, HttpServletResponse.class, String.class);
			Boolean redirectWasSent = (Boolean) method.invoke(myController.newInstance(), request, response, id);

			if (!redirectWasSent) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/view/" + viewName + ".jsp");
				dispatcher.include(request, response);
			}

		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| InstantiationException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	private void getVariablesValuesFromUrl(String[] urlArray) {
		if (urlArray.length > 2)
		{
			viewName = urlArray[2];
			controllerName = urlArray[2].substring(0, 1).toUpperCase() + urlArray[2].substring(1);
		}
		if (urlArray.length > 3)
			action = urlArray[3].substring(0, 1).toLowerCase() + urlArray[3].substring(1);
		else
			action = viewName;
		if (urlArray.length > 4)
			id = urlArray[4];
	}

	private boolean controllerDoesntExists(String controllerName) {
		return Arrays.stream(existingControllersNames).noneMatch(controllerName::equals);
	}

	private boolean urlPointToCssFile(String[] urlArray) {
		return Objects.equals(urlArray[0], "css");
	}

	private String getControllerClassFullPath(String controllerName) {
		return com.costsmanagerhit.config.CONTROLLERS_PACKAGE + "." + controllerName + "Controller";
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
