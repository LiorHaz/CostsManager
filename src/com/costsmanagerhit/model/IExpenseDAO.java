package com.costsmanagerhit.model;

/**
 * Interface to represent the DAO object which is responsible to add/get data to/from DB
 */
public interface IExpenseDAO {
    /**
     * Add the expense to DB
     * @param expense The expense object to add
     * @throws ExpenseDAOException in case of error
     */
    void addExpense(Expense expense) throws ExpenseDAOException;

    /**
     * Get the user expenses by the given month
     * @param month expense's month
     * @param userId user id
     * @return expenses of the required month
     * @throws ExpenseDAOException in case there are no expenses
     */
    Expense[] getUserExpensesByMonth(String month, int userId) throws ExpenseDAOException;

    /**
     * Get the user expenses by search by given parameters
     * @param type expense's type
     * @param month expense's month
     * @param description expense's description
     * @param minAmount expense's minimum amount
     * @param maxAmount expense's maximum amount
     * @param userId user id
     * @return expenses filtered by search of the parameters
     * @throws ExpenseDAOException in case there are no expenses
     */
    Expense[] getUserExpensesBySearch(String type,String month,String description,
                                  double minAmount,double maxAmount,int userId) throws ExpenseDAOException;

    /**
     * Get All the user expenses by user id
     * @param id user id
     * @return all the expenses of the user
     * @throws ExpenseDAOException in case the user has no expenses yet
     */
    Expense[] getUserExpenses(int id) throws ExpenseDAOException;
}
