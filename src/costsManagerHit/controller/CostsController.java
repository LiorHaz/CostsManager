package costsManagerHit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CostsController {

	public void products(HttpServletRequest request, HttpServletResponse response, String data) {
//		Map<Integer,Product> products = ProductsDAO.createInstance().getProducts();
		request.setAttribute("products", "test1");
	}

	public void product(HttpServletRequest request, HttpServletResponse response, String data) {
//		Product product = ProductsDAO.createInstance().getProducts().get(Integer.parseInt(data));
		request.setAttribute("product", "test2");
	}
}
