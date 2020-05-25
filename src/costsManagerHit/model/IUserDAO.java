package costsManagerHit.model;

public interface IUserDAO {
    User validateUser(String username,String password) throws UserDAOException;
    boolean addUser(User user) throws UserDAOException;
    boolean userExistsInDb(String userName) throws UserDAOException, ClassNotFoundException;
}
