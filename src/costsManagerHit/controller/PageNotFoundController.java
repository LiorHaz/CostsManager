package costsManagerHit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageNotFoundController {

    public void pageNotFound(HttpServletRequest request, HttpServletResponse response, String data) {
        System.out.println("Incorrect URL path was entered");
    }

}
