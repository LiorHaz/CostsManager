package com.costsmanagerhit.controller;

import com.costsmanagerhit.model.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ExpensesController {

	/**
	 *
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 */
	public void expenses(HttpServletRequest request, HttpServletResponse response, String data) {
		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			goToLogin(request, response);
			return;
		}
		Expense[] expenses ;
		try {
			expenses = ExpenseDAOHibernate.getInstance().getUserExpenses(user.getId());
			request.setAttribute("expenses", expenses);
			request.setAttribute("allExpenses",true);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 */
	public void filterByMonth(HttpServletRequest request, HttpServletResponse response, String data) {
		String filteredMonth = request.getParameter("filteredMonth");
		try {
			User user=(User)request.getSession().getAttribute("user");
			if(user==null){
				goToLogin(request, response);
				return;
			}
			Expense[] expenses = ExpenseDAOHibernate.getInstance().getUserExpensesByMonth(filteredMonth, user.getId());
			double sum = getExpensesSum(expenses);
			request.setAttribute("expenses", expenses);
			request.setAttribute("month",filteredMonth);
			request.setAttribute("sum",sum);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 */
	public void filterBySearch(HttpServletRequest request, HttpServletResponse response, String data){
		String expenseType=request.getParameter("expenseType");
		String expenseMonth=request.getParameter("expenseMonth");
		String expenseDescription=request.getParameter("expenseDescription");
		double expenseMinAmount=Double.parseDouble(request.getParameter("expenseMinAmount"));
		double expenseMaxAmount=Double.parseDouble(request.getParameter("expenseMaxAmount"));
		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			goToLogin(request, response);
			return;
		}
		try {
			IExpenseDAO iExpenseDAOHibernate=ExpenseDAOHibernate.getInstance();
			Expense[] expenses=iExpenseDAOHibernate.getUserExpensesBySearch(expenseType,expenseMonth,expenseDescription,
					expenseMinAmount,expenseMaxAmount,user.getId());
			request.setAttribute("expenses",expenses);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}

	}
	/**
	 *
	 * @param request The request which was sent to the controller
	 * @param response The response which was sent to the controller
	 * @param data Extra data if needed
	 */
	public void addExpense(HttpServletRequest request, HttpServletResponse response, String data) {
		String type = request.getParameter("expenseType");
		String month = request.getParameter("expenseMonth");
		String description = request.getParameter("expenseDescription");
		double amount = Double.parseDouble(request.getParameter("expenseAmount"));
		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			goToLogin(request, response);
			return;
		}
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
	}
	/**
	 *
	 * @param expenses The expenses array which the summarize will be computed from
	 * @return The total summarize of the expenses
	 */
	private static double getExpensesSum(Expense[] expenses){
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
		String forwardUrl = "/CostsManagerHit/login";
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(forwardUrl);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
