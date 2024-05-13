package ru.kidesoft.ticketplace.client.domain.executor;


public class ThrowableLambda {

    @FunctionalInterface
    public interface CheckedFunction<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface CheckedConsumer<T> {
        void call(T t) throws Exception;
    }

    @FunctionalInterface
    public interface CheckedRunnable {
        void call() throws Exception;
    }
}




