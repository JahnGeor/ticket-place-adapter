package ru.kidesoft.ticketplace.client.domain.executor;

import ru.kidesoft.ticketplace.client.domain.executor.handler.Handler;
import ru.kidesoft.ticketplace.client.domain.executor.handler.impl.DefaultHandler;
import ru.kidesoft.ticketplace.client.domain.presenter.Presenter;

public class Builder {
    private Handler handler;
    private Presenter presenter;

    public Builder withHandler(Handler handler) {
        this.handler = handler;
        return this;
    }

    public Builder withPresenter(Presenter presenter) {
        this.presenter = presenter;
        return this;
    }

     public Executor load() {

        if (handler == null) {
            handler = new DefaultHandler();
        }

        return new Executor(handler, presenter);
    }
}
