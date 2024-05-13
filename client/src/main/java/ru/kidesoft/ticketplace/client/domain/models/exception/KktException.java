package ru.kidesoft.ticketplace.client.domain.models.exception;

public class KktException extends Exception {
    String title;
    Integer code;

    public KktException(String title, String message, Integer code) {
        super(message);
        this.title = title;
        this.code = code;
    }
}
