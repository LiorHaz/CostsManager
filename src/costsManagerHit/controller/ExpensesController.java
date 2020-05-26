package costsManagerHit.controller;

import costsManagerHit.model.Expense;
import costsManagerHit.model.ExpenseDAOException;
import costsManagerHit.model.ExpenseDAOHibernate;
import costsManagerHit.model.IExpenseDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class ExpensesController {

	public void expenses(HttpServletRequest request, HttpServletResponse response, String data) throws ExpenseDAOException {
		Expense[] expenses = ExpenseDAOHibernate.getInstance().getAll();
		double sum = getExspensesSum(expenses);
		request.setAttribute("expenses", expenses);
		request.setAttribute("sum",sum);
	}

	public static void setAttributeLastThreeExpenses(HttpServletRequest request, HttpServletResponse response, String data) throws ExpenseDAOException {
		Expense[] allExpenses = ExpenseDAOHibernate.getInstance().getAll();
		Expense[] lastThreeExpenses = Arrays.copyOfRange(allExpenses, 0, 3);
		request.setAttribute("expenses", lastThreeExpenses);
	}

	public void filterByMonth(HttpServletRequest request, HttpServletResponse response, String data) {
		String filteredMonth = request.getParameter("filteredMonth");
		try {
			Expense[] expenses = ExpenseDAOHibernate.getInstance().getExpensesByMonth(filteredMonth, 1);
			double sum = getExspensesSum(expenses);
			request.setAttribute("expenses", expenses);
			request.setAttribute("sum",sum);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
	}

	public void expense(HttpServletRequest request, HttpServletResponse response, String data) {
		System.out.println("expense in expenses controller");
	}

	public void addExpense(HttpServletRequest request, HttpServletResponse response, String data) {
		String type = request.getParameter("expenseType");
		String month = request.getParameter("expenseMonth");
		String description = request.getParameter("expenseDescription");
		double amount = Double.parseDouble(request.getParameter("expenseAmount"));
		Expense expense = new Expense(amount, type, description, month, 1);

		try {
			IExpenseDAO iExpenseDAOHibernate = ExpenseDAOHibernate.getInstance();
			iExpenseDAOHibernate.addExpense(expense);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
	}

	private static double getExspensesSum(Expense[] expenses){
		double sum=0.0;
		for (Expense expense : expenses)
			sum += expense.getAmount();
		return sum;
	}
}
