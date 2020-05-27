package costsManagerHit.controller;

import costsManagerHit.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExpensesController {

	public void expenses(HttpServletRequest request, HttpServletResponse response, String data) throws ExpenseDAOException {
		User user=(User)request.getSession().getAttribute("user");
		Expense[] expenses = ExpenseDAOHibernate.getInstance().getUserExpenses(user.getId());
		request.setAttribute("expenses", expenses);
		request.setAttribute("allExpenses",true);
	}

	public void filterByMonth(HttpServletRequest request, HttpServletResponse response, String data) {
		String filteredMonth = request.getParameter("filteredMonth");
		try {
			User user=(User)request.getSession().getAttribute("user");
			Expense[] expenses = ExpenseDAOHibernate.getInstance().getUserExpensesByMonth(filteredMonth, user.getId());
			double sum = getExpensesSum(expenses);
			request.setAttribute("expenses", expenses);
			request.setAttribute("month",filteredMonth);
			request.setAttribute("sum",sum);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
	}

	public void filterBySearch(HttpServletRequest request, HttpServletResponse response, String data){
		String expenseType=request.getParameter("expenseType");
		String expenseMonth=request.getParameter("expenseMonth");
		String expenseDescription=request.getParameter("expenseDescription");
		double expenseMinAmount=Double.parseDouble(request.getParameter("expenseMinAmount"));
		double expenseMaxAmount=Double.parseDouble(request.getParameter("expenseMaxAmount"));
		User user=(User)request.getSession().getAttribute("user");
		try {
			IExpenseDAO iExpenseDAOHibernate=ExpenseDAOHibernate.getInstance();
			Expense[] expenses=iExpenseDAOHibernate.getExpensesBySearch(expenseType,expenseMonth,expenseDescription,
					expenseMinAmount,expenseMaxAmount,user.getId());
			request.setAttribute("expenses",expenses);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}

	}

	public void expense(HttpServletRequest request, HttpServletResponse response, String data) {
	}

	public void addExpense(HttpServletRequest request, HttpServletResponse response, String data) {
		String type = request.getParameter("expenseType");
		String month = request.getParameter("expenseMonth");
		String description = request.getParameter("expenseDescription");
		double amount = Double.parseDouble(request.getParameter("expenseAmount"));
		User user=(User)request.getSession().getAttribute("user");
		Expense expense = new Expense(amount, type, description, month, user.getId());

		try {
			IExpenseDAO iExpenseDAOHibernate = ExpenseDAOHibernate.getInstance();
			iExpenseDAOHibernate.addExpense(expense);
			request.setAttribute("addedSuccessfully",true);
			HomeController homeController= new HomeController();
			homeController.home(request,response,data);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
	}

	private static double getExpensesSum(Expense[] expenses){
		double sum=0.0;
		for (Expense expense : expenses)
			sum += expense.getAmount();
		return sum;
	}
}
