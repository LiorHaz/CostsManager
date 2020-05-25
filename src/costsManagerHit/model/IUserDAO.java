package costsManagerHit.model;

public interface IUserDAO {
    boolean addUser(User user) throws UserDAOException;
    boolean userExistsInDb(String userName) throws UserDAOException, ClassNotFoundException;
    boolean nameAndPassMatchDb(String userName, String password);
}