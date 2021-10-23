import com.fasterxml.jackson.databind.ObjectMapper;
import dao.data.UserRepositoryImpl;
import errors.GlobalExceptionHandler;
import dao.repository.UserRepository;
import dao.service.UserService;
import dao.service.impl.UserServiceImpl;

public class Configuration {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final UserRepository USER_REPOSITORY = new UserRepositoryImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl(USER_REPOSITORY);
    private static final GlobalExceptionHandler GLOBAL_ERROR_HANDLER = new GlobalExceptionHandler(OBJECT_MAPPER);

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static UserRepository getUserRepository() {
        return USER_REPOSITORY;
    }

    public static UserService getUserService() {
        return USER_SERVICE;
    }

    public static GlobalExceptionHandler getErrorHandler() {
        return GLOBAL_ERROR_HANDLER;
    }
}
