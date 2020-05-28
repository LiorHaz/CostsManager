package com.costsmanagerhit.model;

public interface IUserDAO {
     /**
      *
      * @param userName the user name to add
      * @param password the password to add
      * @return the user object for the http session object if the registration succeeded, otherwise - null
      * @throws UserDAOException is case the user is already exists
      */
     User addUser(String userName,String password) throws UserDAOException;

     /**
      *
      * @param userName user name to validate
      * @param password password to validate
      * @return the user object if the registration succeeded, otherwise - null
      * @throws UserDAOException in case the user not exists
      */
     User validateUserAndPassword(String userName,String password) throws UserDAOException;

     /**
      *
      * @param userName user name to validate
      * @return true if the user exists, otherwise - false
      * @throws UserDAOException in case the user already exists
      */
     boolean userNameExists(String userName) throws UserDAOException;
}