package ru.kidesoft.desktop.domain.exception;

public class KktException extends AppException {
    private int code;

    public KktException(String message) {
        super(message, AppExceptionType.KktExceptionType);
    }

    public KktException(String message, Throwable cause) {
        super(message, cause, AppExceptionType.KktExceptionType);
    }

    public KktException(String message, int code) {
        super(message, AppExceptionType.KktExceptionType);
        this.code = code;
    }
}
