package ru.kidesoft.desktop.domain.exception;

import lombok.Builder;

public class UnspecifiedException extends AppException {

    public UnspecifiedException(String message) {
        super(message);
    }

    public UnspecifiedException(Throwable cause) {
        super(cause);
    }

    public UnspecifiedException(String message, Throwable cause) {
        super(message, cause);
    }
}
