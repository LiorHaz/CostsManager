package costsManagerHit.model;

public interface IExpenseDAO {
   /*Adds an expense to database*/
    boolean addExpense(Expense expense) throws ExpenseDAOException;
    /*Get expenses for monthly report from database*/
    Expense[] getExpensesByMonth(String month,int userId) throws ExpenseDAOException;
    /*Get expenses by user's search from database*/
    Expense[] getExpensesBySearch(String type,String month,String description,
                                  double minAmount,double maxAmount,int userId) throws ExpenseDAOException;
    /*Get all the expenses from database*/
    Expense[] getAll() throws ExpenseDAOException;
}
