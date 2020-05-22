package costsManagerHit.controller;

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
		String type = request.getParameter("expenseType");
		String month = request.getParameter("expenseMonth");
		String description = request.getParameter("expenseDescription");
		int amount = Integer.parseInt(request.getParameter("expenseAmount"));

		System.out.println("type = " + type);
		System.out.println("month = " + month);
		System.out.println("description = " + description);
		System.out.println("amount = " + amount);

//		TODO pass those parameters to DB
	}
}
