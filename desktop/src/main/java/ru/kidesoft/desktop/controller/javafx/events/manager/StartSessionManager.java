package ru.kidesoft.desktop.controller.javafx.events.manager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import ru.kidesoft.desktop.controller.handler.DefaultHandler;
import ru.kidesoft.desktop.controller.javafx.events.StartSessionEvent;
import ru.kidesoft.desktop.controller.javafx.fxml.main.MainView;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.service.KktService;

@Controller
public class StartSessionManager implements ApplicationListener<StartSessionEvent> {
    private final KktService kktService;
    private ConfigurableApplicationContext context;

    @Autowired
    public StartSessionManager(KktService kktService, ConfigurableApplicationContext context) {
        this.context = context;
        this.kktService = kktService;
    }



    @Override
    public void onApplicationEvent(StartSessionEvent event) {
        try {
            kktService.initialize();
        } catch (AppException e) {
            context.getBean(DefaultHandler.class).handle(e);
        }
        finally {
            context.getBean(StageManager.class).show(MainView.class);
        }
    }

}
