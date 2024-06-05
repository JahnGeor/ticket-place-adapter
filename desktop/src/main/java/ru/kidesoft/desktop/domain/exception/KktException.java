package ru.kidesoft.desktop.domain.exception;

import lombok.Getter;

@Getter
public class KktException extends AppException {
    private int code;

    public KktException(String message) {
        super(message);
    }

    public KktException(String message, Throwable cause) {
        super(message, cause);
    }

    public KktException(String message, int code) {
        super(message);
        this.code = code;
    }
}
