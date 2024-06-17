package ru.kidesoft.desktop.domain.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;


public class ApiException extends AppException {
    @Getter
    private int statusCode;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public ApiException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}
