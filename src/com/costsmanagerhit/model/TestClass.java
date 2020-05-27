package com.costsmanagerhit.model;


public class TestClass {
    public static void main(String[] args) {
        IExpenseDAO iExpenseDAOHibernate;
        IUserDAO iUserDAOHibernate;
        try {
            iExpenseDAOHibernate=ExpenseDAOHibernate.getInstance();
            iExpenseDAOHibernate.addExpense(new Expense(49.90,"321","Buying T-shirt","February",1));
//            iExpenseDAOHibernate.addExpense(new Expense(349.90,"Car","Buying fuel","February",1));
//            Expense[] expenses=iExpenseDAOHibernate.getExpensesBySearch("Car","January","",200,453.56,1);
//            Expense[] expenses = iExpenseDAOHibernate.getAll();
//            Expense[] expenses=iExpenseDAOHibernate.getExpensesByMonth("February",1);
//            for(Expense expense:expenses){
//                System.out.println(expense.toString());
//            }
//            iUserDAOHibernate = UserDAOHibernate.getInstance();
//            boolean result = iUserDAOHibernate.addUser(new User("jony","123456"));
//            System.out.println(user.toString());
//            user=iUserDAOHibernate.addUser(new User("tony","1234"));
//            System.out.println(user.toString());
//            user= iUserDAOHibernate.addUser(new User("james","3456"));
//            System.out.println(user.toString());
//            User user=iUserDAOHibernate.validateUser("tony","1234");
//            if(user!=null)
//                System.out.println(user.toString());
        } catch (ExpenseDAOException e) {
            e.printStackTrace();
        }
    }
}
