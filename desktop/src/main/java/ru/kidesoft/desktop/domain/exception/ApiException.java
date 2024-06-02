package ru.kidesoft.desktop.domain.exception;

import lombok.experimental.StandardException;


public class ApiException extends AppException {

    private int statusCode;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }


    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(String message) {
        super(message);
    }
}
