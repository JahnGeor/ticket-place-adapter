package ru.kidesoft.ticketplace.client.domain.models.exception;

public class WebException extends AppException {


    public WebException(Throwable cause) {
        super(cause);
    }

    public WebException(Integer code, String message) {
        super(message, code);
    }


}
