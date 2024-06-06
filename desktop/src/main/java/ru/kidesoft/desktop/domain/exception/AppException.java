package ru.kidesoft.desktop.domain.exception;

import lombok.Builder;


public abstract class AppException extends Exception {
    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
