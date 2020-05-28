package com.costsmanagerhit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageNotFoundController {
    /**
     *
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     */
    public void pageNotFound(HttpServletRequest request, HttpServletResponse response, String data) {
        System.out.println("Incorrect URL path was entered");
    }
}