package ru.kidesoft.ticketplace.client.domain.models.exception;

public class AppException extends Exception{
    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
