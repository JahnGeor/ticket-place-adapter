package ru.kidesoft.desktop.controller.javafx;

import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import ru.kidesoft.desktop.DesktopApplication;
import ru.kidesoft.desktop.controller.javafx.events.StageReadyEvent;
import ru.kidesoft.desktop.utils.Lock;

import java.io.IOException;

@Controller
public class JavaFxApplication extends Application {
    public ConfigurableApplicationContext context;

    Boolean lock;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        context = new SpringApplicationBuilder().sources(DesktopApplication.class).run(args);
        //Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());

        var styleSheets = "/assets/css/cupertino-light.css";
        Application.setUserAgentStylesheet(styleSheets);
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage) {
        context.publishEvent(new StageReadyEvent(stage));
    }
}
