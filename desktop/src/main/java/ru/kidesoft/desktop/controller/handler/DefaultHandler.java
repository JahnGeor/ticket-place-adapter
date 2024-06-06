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
import ru.kidesoft.desktop.domain.service.KktService;

@Component("defaultHandler")
public class DefaultHandler implements Handler {
    private final KktService kktService;
    ConfigurableApplicationContext applicationContext;

    @Autowired
    public DefaultHandler(ConfigurableApplicationContext applicationContext, KktService kktService, KktService kktService) {
        this.applicationContext = applicationContext;
        this.kktService = kktService;
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
        applicationContext.getBean(StageManager.class).showNotification(e);

        switch (e.getCode()) {
            case IFptr.LIBFPTR_ERROR_DENIED_IN_OPENED_RECEIPT -> {

            }
        }
    }
}
