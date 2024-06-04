package ru.kidesoft.desktop.domain.exception;

public class AppException extends Exception {
    public AppExceptionType appExceptionType;

    public AppException(String message) {
        super(message);
        this.appExceptionType = AppExceptionType.UNDEFINED;
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
        this.appExceptionType = AppExceptionType.UNDEFINED;
    }

    public AppException(String message, AppExceptionType appExceptionType) {
        super(message);
        this.appExceptionType = appExceptionType;
    }

    public AppException(String message, Throwable cause, AppExceptionType appExceptionType) {
        super(message, cause);
        this.appExceptionType = appExceptionType;
    }

    public AppException(Throwable cause, AppExceptionType appExceptionType) {
        super(cause);
        this.appExceptionType = appExceptionType;
    }
}
