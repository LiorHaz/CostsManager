package com.costsmanagerhit.model;

/**
 * Represents an exception in case of a problem in the user management
 */
public class UserDAOException extends Exception {

    public UserDAOException() {
        super();
    }
    /**
     *
     * @param message exception message
     */
    public UserDAOException(String message) {
        super(message);
    }
    /**
     *
     * @param message exception message
     * @param cause throwable object includes the exception cause (optional)
     */
    public UserDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
