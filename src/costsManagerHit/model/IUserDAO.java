package costsManagerHit.model;

public interface IUserDAO {

    /*Validates that the user is in the database*/
    User validateUser(String username,String password) throws UserDAOException;
    /*If the user is not exist , he will be added to the database and will be returned with his id
    * else, the method will return null*/
    User addUser(User user) throws UserDAOException;
}
