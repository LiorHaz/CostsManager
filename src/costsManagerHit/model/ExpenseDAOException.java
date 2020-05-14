package costsManagerHit.model;

public class ExpenseDAOException extends Exception {
    public ExpenseDAOException(){
        super();
    }

    public ExpenseDAOException(String message) {
        super(message);
    }

    public ExpenseDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
