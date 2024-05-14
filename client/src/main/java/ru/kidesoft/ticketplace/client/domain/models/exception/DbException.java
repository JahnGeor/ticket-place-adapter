package ru.kidesoft.ticketplace.client.domain.models.exception;

public class DbException extends AppException {
    public DbException(Throwable cause, String message) {
        super(message, cause);
    }

    public DbException(String message) {
        super(message);
    }

    public DbException(Throwable cause) {
        super(cause);
    }
}
