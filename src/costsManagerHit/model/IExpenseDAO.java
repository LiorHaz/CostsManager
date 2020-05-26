package costsManagerHit.model;

public interface IExpenseDAO {
    void addExpense(Expense expense) throws ExpenseDAOException;
    Expense[] getExpensesByMonth(String month,int userId) throws ExpenseDAOException;
    Expense[] getExpensesBySearch(String type,String month,String description,
                                  double minAmount,double maxAmount,int userId) throws ExpenseDAOException;
    Expense[] getAll() throws ExpenseDAOException;
}
