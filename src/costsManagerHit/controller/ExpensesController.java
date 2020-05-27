package costsManagerHit.controller;

import costsManagerHit.model.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpensesController {

	public boolean expenses(HttpServletRequest request, HttpServletResponse response, String data) throws ExpenseDAOException {
		User user = (User) request.getSession(false).getAttribute("user");
		Expense[] expenses = ExpenseDAOHibernate.getInstance().getUserExpenses(user.getId());
		double sum = getExpensesSum(expenses);
		request.setAttribute("expenses", expenses);
		request.setAttribute("sum",sum);
		return true;
	}

	public boolean filterByMonth(HttpServletRequest request, HttpServletResponse response, String data) {
		User user = (User) request.getSession(false).getAttribute("user");
		String filteredMonth = request.getParameter("filteredMonth");
		try {
			Expense[] expenses = ExpenseDAOHibernate.getInstance().getUserExpensesByMonth(filteredMonth, user.getId());
			double sum = getExpensesSum(expenses);
			request.setAttribute("expenses", expenses);
			request.setAttribute("sum",sum);
			return true;
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void expense(HttpServletRequest request, HttpServletResponse response, String data) {
	}

	private static double getExpensesSum(Expense[] expenses){
		double sum=0.0;
		for (Expense expense : expenses)
			sum += expense.getAmount();
		return sum;
	}
}
