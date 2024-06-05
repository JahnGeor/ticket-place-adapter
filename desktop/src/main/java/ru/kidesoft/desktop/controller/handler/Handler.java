package ru.kidesoft.desktop.controller.handler;

import org.springframework.stereotype.Component;

@Component
public interface Handler {
    void handle(Throwable e);
}
