package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import errors.GlobalExceptionHandler;

import java.io.IOException;
import java.io.InputStream;

public abstract class Handler implements HttpHandler {
    private ObjectMapper objectMapper;
    private GlobalExceptionHandler globalExceptionHandler;

    public Handler(ObjectMapper objectMapper,
                   GlobalExceptionHandler globalExceptionHandler) {
        this.objectMapper = objectMapper;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    public void handle(HttpExchange exchange) {
        try {
            execute(exchange);
        } catch (Exception ex) {
            globalExceptionHandler.handle(ex, exchange);
        }
    }

    protected abstract void execute(HttpExchange exchange) throws Exception;

    protected <T> byte[] writeResponse(T response) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(response);
    }

    protected <T> T readRequest(InputStream is, Class<T> type) throws IOException {
        return objectMapper.readValue(is, type);
    }

    protected static Headers getHeaders(String key, String value) {
        Headers headers = new Headers();
        headers.set(key, value);
        return headers;
    }
}
