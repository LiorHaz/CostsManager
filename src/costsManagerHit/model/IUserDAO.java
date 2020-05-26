package costsManagerHit.model;

public interface IUserDAO {
     User addUser(String userName,String password) throws UserDAOException;
     User validateUserAndPassword(String userName,String password) throws UserDAOException;
     boolean validateUserName(String userName) throws UserDAOException;
}