package ru.kidesoft.desktop.domain.exception;

import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;


public class ApiException extends AppException {

    private int statusCode;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
