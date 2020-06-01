package com.costsmanagerhit.model;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represent the class which implements the IExpenseDAO interface, adds/gets data to/from DB through Hibernate
 */
public class ExpenseDAOHibernate implements IExpenseDAO {

    private static IExpenseDAO instance;
    private final SessionFactory factory;

    private ExpenseDAOHibernate() {
        factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    /**
     *Gets instance of this class
     * @return Instance of this object - singleton
     */
    public static IExpenseDAO getInstance() {
        if(instance==null){
            instance= new ExpenseDAOHibernate();
        }
        return instance;
    }

    /**
     * Add the expense to DB
     * @param expense The expense object to add
     * @throws ExpenseDAOException in case of error
     */
    @Override
    public void addExpense(Expense expense) throws ExpenseDAOException {
        Session session = null;
        try {
            session = factory.openSession();
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            session.beginTransaction();
            session.save(expense);
            session.getTransaction().commit();
        } catch (HibernateException | ClassNotFoundException e) {
            Transaction tx = Objects.requireNonNull(session).getTransaction();
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }

    /**
     * Get the user expenses by the given month
     * @param month expense's month
     * @param userId user id
     * @return expenses of the required month
     * @throws ExpenseDAOException in case there are no expenses
     */
    @Override
    public Expense[] getUserExpensesByMonth(String month, int userId) throws ExpenseDAOException {
        Expense[] expenses =null;
        Session session = null;
        try
        {
            session = factory.openSession();
            session.beginTransaction();
            Query query = session.createQuery(" FROM Expense E WHERE E.month= :month and E.userId= :userId");
            query.setString("month",month).setInteger("userId",userId);
            List<?> expensesList = query.list();
            expenses = listToArray(expensesList);
        }
        catch (HibernateException e)
        {
            Transaction tx = Objects.requireNonNull(session).getTransaction();
            if (tx.isActive()) tx.rollback();
        } finally {
            if (session != null) session.close();
        }
        return expenses;
    }
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
    @Override
    public Expense[] getUserExpensesBySearch(String type, String month, String description, double minAmount,
                                         double maxAmount, int userId) throws ExpenseDAOException {
        Expense[] expenses =null;
        Session session = null;
        try
        {
            session = factory.openSession();
            session.beginTransaction();
            Query query= session.createQuery("FROM Expense E where E.type= :type and E.month= :month and " +
                    "E.description LIKE :description and E.amount>= :minAmount and E.amount<= :maxAmount and " +
                    "E.userId= :userId");
            query.setString("type",type)
                    .setString("month",month)
                    .setString("description","%"+description+"%")
                    .setDouble("minAmount",minAmount)
                    .setDouble("maxAmount",maxAmount)
                    .setInteger("userId",userId);
            List<?> expenses1 = query.list();
            if(expenses1.size()==0)
                throw new ExpenseDAOException("There are no expenses which match to your search");
            expenses= new Expense[expenses1.size()];
            Iterator<?> i = expenses1.iterator();
            int j=0;
            while(i.hasNext()) {
                expenses[j] = (Expense) i.next();
                j++;
            }
        }
        catch (HibernateException e)
        {
            Transaction tx = Objects.requireNonNull(session).getTransaction();
            if (tx.isActive()) tx.rollback();
        }
        catch (ExpenseDAOException e){
            e.printStackTrace();
        }
        finally {
            if (session != null)
                session.close();
        }
        return expenses;
    }

    /**
     * Cast the list raw type which is given from the hibernate into an array
     * @param expensesList casting from expenses list raw type to expenses array
     * @return the expenses as array
     */
    private Expense[] listToArray(List<?> expensesList) {
        Expense[] expenses = new Expense[expensesList.size()];
        Iterator<?> i = expensesList.iterator();
        int j=0;
        while(i.hasNext()) {
            expenses[j] = (Expense) i.next();
            j++;
        }
        return expenses;
    }

    /**
     * Get All the user expenses by user id
     * @param id user id
     * @return all the expenses of the user
     * @throws ExpenseDAOException in case the user has no expenses yet
     */
    @Override
    public Expense[] getUserExpenses(int id) throws ExpenseDAOException {
        Expense[] expenses = null;
        Session session = null;
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            session = factory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM Expense E WHERE E.userId= :id order by id desc");
            query.setString("id", String.valueOf(id));
            List<?> expensesList = query.list();
            expenses = listToArray(expensesList);
        }
        catch (HibernateException | ClassNotFoundException e)
        {
            Transaction tx = Objects.requireNonNull(session).getTransaction();
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            if (session != null)
                session.close();
        }
        return expenses;
    }

}
