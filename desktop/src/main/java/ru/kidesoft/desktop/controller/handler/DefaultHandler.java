package ru.kidesoft.desktop.controller.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import ru.kidesoft.desktop.controller.javafx.events.manager.StageManager;
import ru.kidesoft.desktop.domain.exception.*;
import ru.atol.drivers10.fptr.IFptr;
import ru.kidesoft.desktop.infrastructure.service.KktService;

@Component("defaultHandler")
public class DefaultHandler implements Handler {
    private final KktService kktService;
    ConfigurableApplicationContext applicationContext;

    @Autowired
    public DefaultHandler(ConfigurableApplicationContext applicationContext, KktService kktService) {
        this.applicationContext = applicationContext;
        this.kktService = kktService;
    }

    private final Logger logger = LogManager.getLogger(DefaultHandler.class);
    @Override
    public void handle(Throwable e) {

        if (e == null) {
            return;
        }

        if (e instanceof RuntimeException appException && appException.getCause() != null) {
            e = appException.getCause();
        }

        try {
            if (e instanceof KktException kkt) {
                KktHandle(kkt);
            } else if (e instanceof ApiException api) {
                ApiHandle(api);
            } else if (e instanceof DbException db) {
                DbHandle(db);
            } else if (e instanceof BusinessRulesException business) {
                applicationContext.getBean(StageManager.class).showError(business);
            }
                else {
                logger.error(e.getMessage(), e);
            }
        } catch (AppException ex) {
            applicationContext.getBean(StageManager.class).showError(ex);
        }
    }

    private void KktHandle(KktException e) throws AppException {
        applicationContext.getBean(StageManager.class).showNotification(e);

        if (e.getCode() == IFptr.LIBFPTR_ERROR_DENIED_IN_OPENED_RECEIPT) {
            kktService.cancelReceipt();
            applicationContext.getBean(StageManager.class).showNotification("Незакрытый чек был отменен", "Уведомление");
        }
    }

    private void ApiHandle(ApiException e) throws AppException {
        applicationContext.getBean(StageManager.class).showNotification(e);

        // TODO: Показывать конкретные проблемы
    }


    private void DbHandle(DbException e) throws AppException {
        applicationContext.getBean(StageManager.class).showNotification(e);
    }
}
