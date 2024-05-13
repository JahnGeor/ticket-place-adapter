package ru.kidesoft.ticketplace.client.domain.executor.handler;

import ru.kidesoft.ticketplace.client.domain.presenter.Presenter;

public abstract class Handler {

    public abstract void handle(Throwable e);
}
