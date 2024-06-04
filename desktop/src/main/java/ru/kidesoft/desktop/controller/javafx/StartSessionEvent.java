package ru.kidesoft.desktop.controller.javafx;

import org.springframework.context.ApplicationEvent;
import ru.kidesoft.desktop.domain.entity.login.Login;

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
