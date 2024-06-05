package ru.kidesoft.desktop.controller.javafx.events;

import org.springframework.context.ApplicationEvent;

public class StartSessionEvent extends ApplicationEvent {
    public enum StartSession {
        START,
        REFRESH,
        CANCEL
    }

    public StartSessionEvent(StartSession sessionCommand) {
        super(sessionCommand);
    }
}
