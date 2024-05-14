package ru.kidesoft.ticketplace.client.domain.executor;

import ru.kidesoft.ticketplace.client.domain.executor.handler.Handler;
import ru.kidesoft.ticketplace.client.domain.executor.handler.extendedHandler.ExtendedHandler;
import ru.kidesoft.ticketplace.client.domain.presenter.Presenter;

import java.util.Optional;

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

     public <T, R> Optional<R> execute(ThrowableLambda.ThrowableFunction<T, R> func, T t) {
        try {
            return Optional.ofNullable(func.apply(t));
        } catch (Exception e) {
            handler.handle(e);
            return Optional.empty();
        }
     }

     public void execute(ThrowableLambda.ThrowableRunnable func) {
        try {
            func.call();
        }  catch (Exception e) {
            ExtendedHandler.aExtendedHandler().setHandler(handler).setException(e).setPresenter(presenter).handle();
        }
     }

    public <R> Optional<R> execute(ThrowableLambda.ThrowableSupplier<R> func) {
        try {
            return Optional.ofNullable(func.get());
        }  catch (Exception e) {
            ExtendedHandler.aExtendedHandler().setHandler(handler).setException(e).setPresenter(presenter).handle();
            return Optional.empty();
        }
    }

    public <T> void execute(ThrowableLambda.ThrowableConsumer<T> func, T t) {
        try {
            func.call(t);
        }  catch (Exception e) {
            ExtendedHandler.aExtendedHandler().setHandler(handler).setException(e).setPresenter(presenter).handle();
        }
    }

}
