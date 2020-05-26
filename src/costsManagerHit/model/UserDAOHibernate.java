package costsManagerHit.model;

import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import java.util.Iterator;
import java.util.List;


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

    @Override
    public User validateUserAndPassword(String userName,String password) throws UserDAOException {
        Session session = null;
        User u = null;
        try {
            session = factory.openSession();
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            session.beginTransaction();
            //Checks if the user exists or details are valid
            Query query=session.createQuery("FROM User U WHERE U.username = :username and U.password= :password")
                    .setString("username",userName)
                    .setString("password",password);
            List<?> users = query.list();
            if(users.size()==0)//The user does not exists or wrong password - return null
                throw new UserDAOException("Username '" + userName +"' is not valid or wrong password");
            //The user exists - return him
            u = (User)users.get(0);
        }
        catch (HibernateException e)
        {
            Transaction tx = session.getTransaction();
            if (tx.isActive()) tx.rollback();
        }
        catch (UserDAOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally
        {
            if(session!=null) session.close();
        }
        return u;
    }

    @Override
    public User addUser(String userName,String password) throws UserDAOException {
        Session session = null;
        User u = null;
        try {
            session = factory.openSession();
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            session.beginTransaction();

            if(!validateUserName(userName))
                throw new UserDAOException("Username '" + userName +"' already exists - try another username");

            //Saves the user in database and returning him with id for the session object
            User user=new User(userName,password);
            session.save(user);
            session.getTransaction().commit();
            Query query=session.createQuery("from User U where U.username= :username")
                    .setString("username",userName);
            List<?> users=query.list();
            Iterator<?> i=users.iterator();
            u=(User)users.get(0);
        }
        catch (HibernateException e)
        {
            Transaction tx = session.getTransaction();
            if (tx.isActive()) tx.rollback();
        }
        catch (UserDAOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally
        {
            if(session!=null) session.close();
        }
        return u;
    }

    @Override
    public boolean validateUserName(String userName) throws UserDAOException {
        Session session = null;
        try
        {
            session = factory.openSession();
            session.beginTransaction();

            //Checks if the user exists before adding him
            Query query=session.createQuery("FROM User U WHERE U.username = :username")
                    .setString("username",userName);
            List<?> users = query.list();
            if(users.size()!=0)//The user exists - return null
                return false;
        }
        catch (HibernateException e)
        {
            Transaction tx = session.getTransaction();
            if (tx.isActive()) tx.rollback();
        }
        finally
        {
            if(session!=null) session.close();
        }
        return true;
    }
}
