package com.costsmanagerhit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageNotFoundController {
    /**
     * Action function to move to pageNotFound
     * @param request The request which was sent to the controller
     * @param response The response which was sent to the controller
     * @param data Extra data if needed
     * @return false
     */
    public boolean pageNotFound(HttpServletRequest request, HttpServletResponse response, String data) {
        return false;
    }
}
