package ru.kidesoft.ticketplace.client.domain.models.exception;

public class AppException extends Exception{
    private Integer code;
    private String description;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public AppException(String message, Integer code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public AppException(String message, Integer code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public AppException(String message, Integer code, String description, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.description = description;
    }
}
