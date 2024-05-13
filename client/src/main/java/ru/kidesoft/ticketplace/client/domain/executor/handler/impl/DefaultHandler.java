package ru.kidesoft.ticketplace.client.domain.executor.handler.impl;

import org.h2.jdbc.JdbcException;
import ru.kidesoft.ticketplace.client.domain.executor.handler.Handler;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.domain.models.exception.KktException;
import ru.kidesoft.ticketplace.client.domain.models.exception.WebException;
import ru.kidesoft.ticketplace.client.domain.presenter.Presenter;


public class DefaultHandler extends Handler {
    @Override
    public void handle(Throwable exception) {
        if (exception instanceof KktException e) {
            kktHandler(e);
            return;
        }

        if (exception instanceof WebException e) {
            webHandler(e);
            return;
        }

        if (exception instanceof DbException e) {
            dbHandler(e);
            return;
        }

        defaultHandler(exception);

    }

    private void kktHandler(KktException kktException) {

    }

    private void webHandler(WebException webException) {

    }

    private void dbHandler(DbException dbException) {

    }

    private void defaultHandler(Throwable throwable) {
        //TODO:
        System.out.println("Uncased");
    }
}
