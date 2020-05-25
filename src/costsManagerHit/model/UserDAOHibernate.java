package costsManagerHit.model;

import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class UserDAOHibernate implements IUserDAO{

    private static IUserDAO instance;
    private SessionFactory factory;

    private UserDAOHibernate() {
        factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    public static IUserDAO getInstance() {
        if(instance==null){
            instance= new UserDAOHibernate();
        }
        return instance;
    }

    public boolean nameAndPassMatchDb(String userName, String password)
    {
        Session session = factory.openSession();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            session.beginTransaction();
            Query query=session.createQuery("FROM User U WHERE U.username = :username and U.password= :password")
                    .setString("username",userName)
                    .setString("password",password);
            List<?> users = query.list();
            if (users.size() == 0)
                return false;
        } catch (HibernateException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(session != null)
                session.close();
        }
        return true;
    }

    @Override
    public boolean addUser(User user) {
        Session session = null;
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            session = factory.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        catch (HibernateException | ClassNotFoundException e)
        {
            Transaction tx = Objects.requireNonNull(session).getTransaction();
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        }
        finally
        {
            if(session != null)
                session.close();
        }
        return false;
    }

    public boolean userExistsInDb(String userName) {
        Session session = factory.openSession();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            session.beginTransaction();
            Query query = session.createQuery("FROM User U WHERE U.username = :username").setString("username", userName);
            List<?> users = query.list();
            if (users.size() == 0)
                return false;
        } catch (HibernateException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(session != null)
                session.close();
        }
        return true;
    }
}
