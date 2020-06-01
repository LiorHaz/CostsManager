package com.costsmanagerhit.controller;

import com.costsmanagerhit.model.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


/**
 * Represents the expenses controller, which connects  and passes data between the jsp pages and the model objects
 */
public class ExpensesController {

	/**
	 *
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 */
	public boolean expenses(HttpServletRequest request, HttpServletResponse response, String data) {
		/*Ensures that the user is still logged in*/
		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			goToLogin(request, response);
			return true;
		}

		/*Gets all user's expenses from DB */
		Expense[] expenses ;
		try {
			expenses = ExpenseDAOHibernate.getInstance().getUserExpenses(user.getId());
			request.setAttribute("expenses", expenses);
			request.setAttribute("allExpenses",true);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 */
	public boolean filterByMonth(HttpServletRequest request, HttpServletResponse response, String data) {

		/*Ensures that the user is still logged in*/
		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			goToLogin(request, response);
			return true;
		}

		/*Gets the data from DB and sets is into request's attributes*/
		try {
			String filteredMonth = request.getParameter("filteredMonth");
			Expense[] expenses = ExpenseDAOHibernate.getInstance().getUserExpensesByMonth(filteredMonth, user.getId());
			double sum = getExpensesSum(expenses);
			request.setAttribute("expenses", expenses);
			request.setAttribute("month",filteredMonth);
			request.setAttribute("sum",sum);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 */
	public boolean filterBySearch(HttpServletRequest request, HttpServletResponse response, String data){
		/*Ensures that the user is still logged in*/
		User user=(User)request.getSession().getAttribute("user");
		if(user==null) {
			goToLogin(request, response);
			return true;
		}

		/*Gets the data from DB and sets is into request's attributes*/
		try {
			String expenseType=request.getParameter("expenseType");
			String expenseMonth=request.getParameter("expenseMonth");
			String expenseDescription=request.getParameter("expenseDescription");
			double expenseMinAmount=Double.parseDouble(request.getParameter("expenseMinAmount"));
			double expenseMaxAmount=Double.parseDouble(request.getParameter("expenseMaxAmount"));
			IExpenseDAO iExpenseDAOHibernate=ExpenseDAOHibernate.getInstance();
			Expense[] expenses=iExpenseDAOHibernate.getUserExpensesBySearch(expenseType,expenseMonth,expenseDescription,
					expenseMinAmount,expenseMaxAmount,user.getId());
			request.setAttribute("expenses",expenses);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 */
	public boolean addExpense(HttpServletRequest request, HttpServletResponse response, String data) {

		/*Ensures that the user is still logged in*/
		User user=(User)request.getSession().getAttribute("user");
		if(user == null){
			goToLogin(request, response);
			return true;
		}

		/*Adds the data to DB and sets is into request's attribute the last three expenses*/
		String type = request.getParameter("expenseType");
		String month = request.getParameter("expenseMonth");
		String description = request.getParameter("expenseDescription");
		double amount = Double.parseDouble(request.getParameter("expenseAmount"));
		Expense expense = new Expense(amount, type, description, month, user.getId());
		try {
			IExpenseDAO iExpenseDAOHibernate = ExpenseDAOHibernate.getInstance();
			iExpenseDAOHibernate.addExpense(expense);
			request.setAttribute("addedSuccessfully",true);
			Expense[] allExpenses = iExpenseDAOHibernate.getUserExpenses(user.getId());
			Expense[] lastThreeExpenses = splitFirstThreeElements(allExpenses);
			request.setAttribute("expenses", lastThreeExpenses);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *
	 * @param expenses The expenses array which the summarize will be computed from
	 * @return The total summarize of the expenses
	 */
	private static double getExpensesSum(Expense[] expenses){
		/*Calculates the summarize of the expenses at the given parameter*/
		double sum=0.0;
		for (Expense expense : expenses)
			sum += expense.getAmount();
		return sum;
	}

	/**
	 *
	 * @param allExpenses The expenses array which will be split
	 * @return The last three expenses as array
	 */
	private Expense[] splitFirstThreeElements(Expense[] allExpenses) {
		/*Splits the first three expenses from the given expenses array parameter*/
		Expense[] firstThreeElements;
		if (allExpenses == null)
			firstThreeElements = new Expense[0];
		else {
			if (allExpenses.length < 3)
				firstThreeElements = Arrays.copyOfRange(allExpenses, 0, allExpenses.length);
			else
				firstThreeElements = Arrays.copyOfRange(allExpenses, 0, 3);
		}
		return firstThreeElements;
	}

	/**
	 *
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 *
	 */
	private void goToLogin(HttpServletRequest request, HttpServletResponse response){
		/*Redirecting to login page*/
		String forwardUrl = "/CostsManagerHit/login";
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(forwardUrl);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
