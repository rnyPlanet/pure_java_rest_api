package errors;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private int code;

    ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }
}
