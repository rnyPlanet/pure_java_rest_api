package handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import dao.models.User;
import dao.models.dto.NewUser;
import dao.service.UserService;
import errors.ApplicationExceptions;
import errors.GlobalExceptionHandler;
import utils.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserHandler extends Handler  {

    private UserService userService;

    public UserHandler(UserService userService,
                       ObjectMapper objectMapper,
                       GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);

        this.userService = userService;
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        byte[] response = new byte[0];

        if ("GET".equals(exchange.getRequestMethod())) {
            ResponseEntity<User> e = doGet(exchange.getRequestURI().getRawQuery());
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
            response = super.writeResponse(e.getBody());

        } else if ("POST".equals(exchange.getRequestMethod())) {
            ResponseEntity<RegistrationResponse> e = doPost(exchange.getRequestBody());
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
            response = super.writeResponse(e.getBody());

        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    private ResponseEntity<User> doGet(String query) {
        Map<String, List<String>> params = ApiUtils.splitQuery(query);
        String id = params.getOrDefault("id", Collections.singletonList(null)).stream().findFirst().orElse(null);

        User user = userService.findById(id);

        return new ResponseEntity<>(user, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }

    private ResponseEntity<RegistrationResponse> doPost(InputStream is) throws IOException {
        RegistrationRequest registerRequest = super.readRequest(is, RegistrationRequest.class);

        NewUser user = NewUser.builder()
                .login(registerRequest.getLogin())
                .password(registerRequest.getPassword())
                .build();

        String userId = userService.create(user);

        RegistrationResponse response = new RegistrationResponse(userId);

        return new ResponseEntity<>(response, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}
