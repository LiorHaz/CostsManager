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
	 * Action for expenses view, setting expenses attribute
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 * @return boolean returns if redirect was sent
	 */
	public boolean expenses(HttpServletRequest request, HttpServletResponse response, String data) {
		User user = (User)request.getSession().getAttribute("user");
		try {
			if(user == null){
				response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
				return true;
			}
			else
				setUserExpensesAttribute(user, request);
		} catch (ExpenseDAOException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Set "expenses" in the session with all of the user expenses
	 * @param user The logged in user
	 * @param request The request sent from client
	 */
	private void setUserExpensesAttribute(User user, HttpServletRequest request) throws ExpenseDAOException {
		Expense[] expenses = ExpenseDAOHibernate.getInstance().getUserExpenses(user.getId());
		request.setAttribute("expenses", expenses);
	}

	/**
	 * Filter the expenses page by month
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 * @return boolean returns if redirect was sent
	 */
	public boolean filterByMonth(HttpServletRequest request, HttpServletResponse response, String data) {
		User user=(User)request.getSession().getAttribute("user");
		/*Gets the data from DB and sets is into request's attributes*/
		try {
			/*Ensures that the user is still logged in*/
			if(user == null){
				response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
				return true;
			}
			else {
				String filteredMonth = request.getParameter("filteredMonth");
				Expense[] expenses = ExpenseDAOHibernate.getInstance().getUserExpensesByMonth(filteredMonth, user.getId());
				double sum = getExpensesSum(expenses);
				request.setAttribute("expenses", expenses);
				request.setAttribute("month", filteredMonth);
				request.setAttribute("sum", sum);
			}
		} catch (ExpenseDAOException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Filter the expenses page by specific categories
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 * @return boolean returns if redirect was sent
	 */
	public boolean filterBySearch(HttpServletRequest request, HttpServletResponse response, String data){
		User user = (User)request.getSession().getAttribute("user");
		try {
			/*Ensures that the user is still logged in*/
			if(user == null) {
				response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
				return true;
			}
			/*Gets the data from DB and sets is into request's attributes*/
			else {
				String expenseType=request.getParameter("expenseType");
				String expenseMonth=request.getParameter("expenseMonth");
				String expenseDescription=request.getParameter("expenseDescription");
				double expenseMinAmount=Double.parseDouble(request.getParameter("expenseMinAmount"));
				double expenseMaxAmount=Double.parseDouble(request.getParameter("expenseMaxAmount"));
				IExpenseDAO iExpenseDAOHibernate=ExpenseDAOHibernate.getInstance();
				Expense[] expenses=iExpenseDAOHibernate.getUserExpensesBySearch(expenseType,expenseMonth,expenseDescription,
						expenseMinAmount,expenseMaxAmount,user.getId());
				request.setAttribute("expenses",expenses);
			}
		} catch (ExpenseDAOException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Add an expense into the DB
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 * @return boolean returns if redirect was sent
	 */
	public boolean addExpense(HttpServletRequest request, HttpServletResponse response, String data) {
		User user = (User)request.getSession().getAttribute("user");
		String type = request.getParameter("expenseType");
		String month = request.getParameter("expenseMonth");
		String description = request.getParameter("expenseDescription");
		double amount = Double.parseDouble(request.getParameter("expenseAmount"));
		Expense expense = new Expense(amount, type, description, month, user.getId());
		try {
			/*Ensures that the user is still logged in*/
			if(user == null) {
				response.sendRedirect("http://localhost:8010/CostsManagerHit/login");
				return true;
			}
			else {
				IExpenseDAO iExpenseDAOHibernate = ExpenseDAOHibernate.getInstance();
				iExpenseDAOHibernate.addExpense(expense);
				request.setAttribute("addedSuccessfully",true);
				response.sendRedirect("http://localhost:8010/CostsManagerHit/home");
			}
		} catch (ExpenseDAOException | IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Return the expenses array sum of "Amount"
	 * @param expenses The expenses array which the sum will be computed from
	 * @return The total sum of the expenses
	 */
	private static double getExpensesSum(Expense[] expenses){
		/*Calculates the summarize of the expenses at the given parameter*/
		double sum=0.0;
		for (Expense expense : expenses)
			sum += expense.getAmount();
		return sum;
	}

	/**
	 * Return a new array composed of the first three elements in allExpenses array
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
}
