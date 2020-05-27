package com.costsmanagerhit.model;

public interface IExpenseDAO {
    /**
     *
     * @param expense The expense object to add
     * @throws ExpenseDAOException in case of error
     */
    void addExpense(Expense expense) throws ExpenseDAOException;

    /**
     *
     * @param month expense's month
     * @param userId user id
     * @return expenses of the required month
     * @throws ExpenseDAOException in case there are no expenses
     */
    Expense[] getUserExpensesByMonth(String month, int userId) throws ExpenseDAOException;

    /**
     *
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
     *
     * @param id user id
     * @return all the expenses of the user
     * @throws ExpenseDAOException in case the user has no expenses yet
     */
    Expense[] getUserExpenses(int id) throws ExpenseDAOException;
}
