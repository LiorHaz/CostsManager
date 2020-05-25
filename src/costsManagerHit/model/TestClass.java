package costsManagerHit.model;

import org.hibernate.cfg.IndexOrUniqueKeySecondPass;

public class TestClass {
    public static void main(String[] args) {
        IExpenseDAO iExpenseDAOHibernate = null;
        IUserDAO iUserDAOHibernate = null;
        try {
            iExpenseDAOHibernate=ExpenseDAOHibernate.getInstance();
            //iExpenseDAOHibernate.addExpense(new Expense(89.90,"Car","Fix the car","February",1));
            //iExpenseDAOHibernate.addExpense(new Expense(349.90,"Car","Buying fuel","February",1));
            //Expense[] expenses=iExpenseDAOHibernate.getExpensesBySearch("Car","January","",200,453.56,1);
            //Expense[] expenses=iExpenseDAOHibernate.getExpensesBySearch("Car","February","",0.0,1000.0,1);
            //iExpenseDAOHibernate.getAll();
            //Expense[] expenses=iExpenseDAOHibernate.getExpensesByMonth("February",1);
           /* for(Expense expense:expenses){
                System.out.println(expense.toString());
            }*/
              iUserDAOHibernate=UserDAOHibernate.getInstance();
              //User user=iUserDAOHibernate.addUser("mony","1236");
             // System.out.println(user.toString());
//            user=iUserDAOHibernate.addUser("tony","1234");
//            System.out.println(user.toString());
//            user= iUserDAOHibernate.addUser("james","3456");
//            System.out.println(user.toString());
              //User user=iUserDAOHibernate.validateUser("mony","1243");
              /*if(user!=null)
                  System.out.println(user.toString());*/
        } catch (ExpenseDAOException|UserDAOException e) {
            e.printStackTrace();
        }
    }
}
