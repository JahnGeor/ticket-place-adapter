package ru.kidesoft.desktop.controller.javafx;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import ru.kidesoft.desktop.controller.javafx.fxml.MainController;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.service.KktService;

@Controller
public class StartSessionListener implements ApplicationListener<StartSessionEvent> {
    private final KktService kktService;
    private ConfigurableApplicationContext context;

    @Autowired
    public StartSessionListener(KktService kktService, ConfigurableApplicationContext context) {
        this.context = context;
        this.kktService = kktService;
    }


    @Override
    public void onApplicationEvent(StartSessionEvent event) {
        try {
            kktService.initialize();
        } catch (AppException e) {
            context.getBean(StageManager.class).showError(e);
        }

        context.getBean(StageManager.class).show(MainController.class);
    }
}
