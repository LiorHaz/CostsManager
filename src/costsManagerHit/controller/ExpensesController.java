package costsManagerHit.controller;

import costsManagerHit.model.Expense;
import costsManagerHit.model.ExpenseDAOException;
import costsManagerHit.model.ExpenseDAOHibernate;
import costsManagerHit.model.IExpenseDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExpensesController {

	public void expenses(HttpServletRequest request, HttpServletResponse response, String data) {
		System.out.println("expenses in expenses controller");
//		Map<Integer,Product> products = ProductsDAO.createInstance().getProducts();
//		request.setAttribute("products", "test1");
	}

	public void expense(HttpServletRequest request, HttpServletResponse response, String data) {
		System.out.println("expense in expenses controller");
//		TODO pull expenses from DB (first need to complete addExpense)
//		Product product = ProductsDAO.createInstance().getProducts().get(Integer.parseInt(data));
		request.setAttribute("product", "test2");
	}

	public void addExpense(HttpServletRequest request, HttpServletResponse response, String data) {
//		TODO check if parameters are legit
		String type = request.getParameter("expenseType");
		String month = request.getParameter("expenseMonth");
		String description = request.getParameter("expenseDescription");
		int amount = Integer.parseInt(request.getParameter("expenseAmount"));
		Expense expense = new Expense(amount, type, description, month, 1);


		IExpenseDAO iExpenseDAOHibernate=null;

		try {
			iExpenseDAOHibernate = ExpenseDAOHibernate.getInstance();
			iExpenseDAOHibernate.addExpense(expense);
		} catch (ExpenseDAOException e) {
			e.printStackTrace();
		}
	}
}
