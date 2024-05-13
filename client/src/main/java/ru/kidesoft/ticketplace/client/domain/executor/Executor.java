package ru.kidesoft.ticketplace.client.domain.executor;

import ru.kidesoft.ticketplace.client.domain.executor.handler.Handler;
import ru.kidesoft.ticketplace.client.domain.executor.handler.extendedHandler.ExtendedHandler;
import ru.kidesoft.ticketplace.client.domain.executor.handler.impl.DefaultHandler;
import ru.kidesoft.ticketplace.client.domain.presenter.Presenter;

public class Executor {
    private Handler handler;
    private Presenter presenter;

    public static Builder builder() {
        return new Builder();
    }

    public Executor(Handler handler, Presenter presenter) {
        this.handler = handler;
        this.presenter = presenter;
    }

     public <T, R> R execute(ThrowableLambda.CheckedFunction<T, R> func, T t) {
        try {
            return func.apply(t);
        } catch (Exception e) {
            handler.handle(e);
            return null;
        }
     }

     public void execute(ThrowableLambda.CheckedRunnable func) {
        try {
            func.call();
        }  catch (Exception e) {
            ExtendedHandler.aExtendedHandler().setHandler(handler).setException(e).setPresenter(presenter).handle();
        }
     }


}
