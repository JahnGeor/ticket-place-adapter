package ru.kidesoft.desktop.controller.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.function.ThrowingConsumer;

@Component
public class HandlerManager {

    private Handler handler;

    public HandlerManager(@Qualifier("defaultHandler") Handler handler) {
        this.handler = handler;
    }

    public <T> void handle(T t, ThrowingConsumer<T> consumer) {
        try {
            consumer.accept(t);
        } catch (Exception e) {
            handler.handle(e);
        }
    }
}
