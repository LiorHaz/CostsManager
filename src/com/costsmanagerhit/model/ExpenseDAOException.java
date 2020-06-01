package com.costsmanagerhit.model;

/**
 * Represents an exception in case of a problem in the expense management
 */
public class ExpenseDAOException extends Exception {

    public ExpenseDAOException(){
        super();
    }

    /**
     *
     * @param message exception message
     */
    public ExpenseDAOException(String message) {
        super(message);
    }

    /**
     *
     * @param message exception message
     * @param cause throwable object includes the exception cause (optional)
     */
    public ExpenseDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
