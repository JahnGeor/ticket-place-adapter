package ru.kidesoft.desktop.controller.javafx.events.manager;


import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import ru.kidesoft.desktop.controller.handler.DefaultHandler;
import ru.kidesoft.desktop.controller.javafx.events.StartSessionEvent;
import ru.kidesoft.desktop.controller.javafx.fxml.main.ListenerService;
import ru.kidesoft.desktop.controller.javafx.fxml.main.MainView;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.service.KktService;
import ru.kidesoft.desktop.domain.service.entities.ClickService;
import ru.kidesoft.desktop.domain.service.entities.SettingService;

@Controller
public class StartSessionManager implements ApplicationListener<StartSessionEvent> {
    private final KktService kktService;
    private final ClickService clickService;
    private final ListenerService listenerService;
    private final SettingService settingService;
    private ConfigurableApplicationContext context;

    @Autowired
    public StartSessionManager(KktService kktService, ConfigurableApplicationContext context, ClickService clickService, ListenerService listenerService, SettingService settingService) {
        this.context = context;
        this.kktService = kktService;
        this.clickService = clickService;
        this.listenerService = listenerService;
        this.settingService = settingService;
    }



    @Override
    public void onApplicationEvent(StartSessionEvent event) {
        try {
            kktService.initialize();

            clickService.clickInitialize();

            var setting = settingService.getCurrentSetting();

            listenerService.setPeriod(Duration.seconds(setting.getServerRequestInterval().toSeconds()));

            listenerService.restart();

        } catch (AppException e) {
            context.getBean(DefaultHandler.class).handle(e);
        }
        finally {
            context.getBean(StageManager.class).show(MainView.class);
        }
    }

}
