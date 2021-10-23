package errors.user;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(String id) {
        super("User " + id + " does not exist.");
    }
}
