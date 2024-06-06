package ru.kidesoft.desktop.domain.exception;

import lombok.Builder;

public class BusinessRulesException extends AppException {
    public BusinessRulesException(String message) {
        super(message);
    }

    public BusinessRulesException(Throwable cause) {
        super(cause);
    }

    public BusinessRulesException(String message, Throwable cause) {
        super(message, cause);
    }
}
