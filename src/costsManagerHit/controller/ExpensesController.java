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
//		Product product = ProductsDAO.createInstance().getProducts().get(Integer.parseInt(data));
		request.setAttribute("product", "test2");
	}
}
