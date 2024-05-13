package ru.kidesoft.ticketplace.client.domain.presenter;

public interface Presenter {
    <T> void update(T t);
    void sendAlert(String header, String message, Exception exception);
}
