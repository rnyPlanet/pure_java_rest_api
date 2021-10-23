import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import handlers.UserHandler;
import utils.ApiUtils;
import utils.Constants;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(Constants.SERVER_PORT), 0);

        createContexts(server);

        server.setExecutor(null);
        server.start();
    }

    private static void createContexts(HttpServer server) {

        server.createContext("/api/first", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String responseText = "Hello!";
                exchange.sendResponseHeaders(200, responseText.getBytes().length);

                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        }));

        server.createContext("/api/second", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());

                String noNameParam = "incognito";
                String name = params.getOrDefault("name", Collections.singletonList(noNameParam)).stream().findFirst().orElse(noNameParam);

                String responseText = String.format("Hello %s!", name);
                exchange.sendResponseHeaders(200, responseText.getBytes().length);

                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        }));

        UserHandler userHandler = new UserHandler(
                Configuration.getUserService(),
                Configuration.getObjectMapper(),
                Configuration.getErrorHandler()
        );
        server.createContext("/api/users", userHandler);
    }
}
