package ru.kidesoft.ticketplace.client.domain.executor.handler.extendedHandler;

import ru.kidesoft.ticketplace.client.domain.executor.handler.Handler;
import ru.kidesoft.ticketplace.client.domain.presenter.Presenter;

public class ExtendedHandler {
    Handler handler;

    Presenter presenter;

    Exception exception;

    public static ExtendedHandler aExtendedHandler() {
        return new ExtendedHandler();
    }

    public ExtendedHandler setHandler(Handler handler) {
        this.handler = handler;
        return this;
    }

    public ExtendedHandler setPresenter(Presenter presenter) {
        this.presenter = presenter;
        return this;
    }

    public ExtendedHandler setException(Exception exception) {
        this.exception = exception;
        return this;
    }

    public void handle() {
        handler.handle(exception);
    }
}
