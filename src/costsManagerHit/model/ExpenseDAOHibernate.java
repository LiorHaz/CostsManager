package costsManagerHit.model;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExpenseDAOHibernate implements IExpenseDAO {

    private static IExpenseDAO instance;
    private SessionFactory factory;

    private ExpenseDAOHibernate() throws ExpenseDAOException{
        factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    public static IExpenseDAO getInstance() throws ExpenseDAOException{
        if(instance==null){
            instance= new ExpenseDAOHibernate();
        }
        return instance;
    }

    @Override
    public boolean addExpense(Expense expense) throws ExpenseDAOException {
        Session session = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            //            TODO Class.forName is a work around for driver not being loaded need to find a solution

            session = factory.openSession();
            session.beginTransaction();
            session.save(expense);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException | ClassNotFoundException e) {
            Transaction tx = Objects.requireNonNull(session).getTransaction();
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Expense[] getExpensesByMonth(String month, int userId) throws ExpenseDAOException {
        Expense[] expenses =null;
        Session session = null;
        try
        {
            session = factory.openSession();
            session.beginTransaction();
//            TODO finish this part has errors
//            Query query = session.createQuery(" FROM Expense E WHERE E.month= :month and E.userId= :userId");
//            query.setString("month",month).setInteger("userId",userId);
//            List expenses1 =query.list();
//            if(expenses1.size()==0)
//                throw new ExpenseDAOException("There are no expenses in "+month);
//            expenses = new Expense[expenses1.size()];
//            Iterator i = expenses1.iterator();
//            int j=0;
//            while(i.hasNext()) {
//                expenses[j] = (Expense) i.next();
//                j++;
//            }
        }
        catch (HibernateException e)
        {
            Transaction tx = Objects.requireNonNull(session).getTransaction();
            if (tx.isActive()) tx.rollback();
        }
        finally {
            if (session != null) session.close();
        }
        return expenses;
    }

    @Override
    public Expense[] getExpensesBySearch(String type, String month, String description, double minAmount,
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
            List expenses1 = query.list();
            if(expenses1.size()==0)
                throw new ExpenseDAOException("There are no expenses which match to your search");
            expenses= new Expense[expenses1.size()];
            Iterator i = expenses1.iterator();
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

    @Override
    public Expense[] getAll() throws ExpenseDAOException {
        Expense[] expenses = null;
        Session session = null;
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            session = factory.openSession();
            session.beginTransaction();
//            TODO quary return null need to pull correct data
            List expensesList = session.createQuery("from Expense").list();
            if(expensesList.size() == 0)
                throw new ExpenseDAOException("There are no expenses yet");
            expenses = new Expense[expensesList.size()];
            Iterator i = expensesList.iterator();
            int j=0;
            while(i.hasNext()) {
                expenses[j] = (Expense) i.next();
                j++;
            }
        }
        catch (HibernateException | ExpenseDAOException | ClassNotFoundException e)
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
