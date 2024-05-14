package ru.kidesoft.ticketplace.client.domain.executor;


public class ThrowableLambda {

    @FunctionalInterface
    public interface ThrowableFunction<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface ThrowableConsumer<T> {
        void call(T t) throws Exception;
    }

    @FunctionalInterface
    public interface ThrowableRunnable {
        void call() throws Exception;
    }

    public interface ThrowableSupplier<R> {
        R get() throws Exception;
    }
}




