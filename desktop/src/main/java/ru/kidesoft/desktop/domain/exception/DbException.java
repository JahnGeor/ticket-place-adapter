package ru.kidesoft.desktop.domain.exception;

public class DbException extends AppException{

    public DbException(Exception e) {
        super(e);
    }
    public DbException(String message) {
        super(message);
    }
}
