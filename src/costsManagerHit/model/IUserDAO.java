package costsManagerHit.model;

public interface IUserDAO {
     User addUser(String userName,String password) throws UserDAOException;
     User validateUser(String userName,String password) throws UserDAOException;
}