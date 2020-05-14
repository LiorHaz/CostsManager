package costsManagerHit.model;

import org.apache.catalina.ha.session.DeltaSession;
import org.hibernate.cfg.IndexOrUniqueKeySecondPass;

public class TestClass {
    public static void main(String[] args) {
        IExpenseDAO iExpenseDAOHibernate=null;
        IUserDAO iUserDAOHibernate=null;
        try {
            iExpenseDAOHibernate=ExpenseDAOHibernate.getInstance();
            //iExpenseDAOHibernate.addExpense(new Expense(49.90,"Housing","Buying T-shirt","February",1));
            //iExpenseDAOHibernate.addExpense(new Expense(349.90,"Car","Buying fuel","February",1));
           /* Expense[] expenses=iExpenseDAOHibernate.getExpensesBySearch("Car","January","",400,453.56,1);
            for(Expense e:expenses){
                System.out.println(e.toString());
            }*/
            iUserDAOHibernate=UserDAOHibernate.getInstance();
           /* user=iUserDAOHibernate.addUser(new User("jony","123456"));
            System.out.println(user.toString());
            user=iUserDAOHibernate.addUser(new User("tony","1234"));
            System.out.println(user.toString());
            user= iUserDAOHibernate.addUser(new User("james","3456"));
            System.out.println(user.toString());*/
            iUserDAOHibernate.addUser(new User("tony","1234"));
        } catch (ExpenseDAOException e) {
            e.printStackTrace();
        } catch (UserDAOException e) {
            e.printStackTrace();
        }
    }
}
