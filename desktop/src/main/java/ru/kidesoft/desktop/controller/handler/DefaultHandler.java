package ru.kidesoft.desktop.controller.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import ru.kidesoft.desktop.controller.javafx.events.manager.StageManager;
import ru.kidesoft.desktop.domain.exception.KktException;
import ru.atol.drivers10.fptr.Fptr;
import ru.atol.drivers10.fptr.IFptr;

@Component("defaultHandler")
public class DefaultHandler implements Handler {
    ConfigurableApplicationContext applicationContext;

    @Autowired
    public DefaultHandler(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private final Logger logger = LogManager.getLogger(DefaultHandler.class);
    @Override
    public void handle(Throwable e) {
        if (e.getCause() instanceof KktException kkt) {
            KktHandle(kkt);
        } else {
            logger.error(e.getMessage(), e);
        }
    }

    private void KktHandle(KktException e) {
        logger.trace("{} - {} - {}", "KKT", e.getCode(), e.getMessage());

        switch (e.getCode()) {
            default: applicationContext.getBean(StageManager.class).showError(e);
        }




    }
}
