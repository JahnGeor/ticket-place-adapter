package ru.kidesoft.desktop.controller.javafx;

import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import ru.kidesoft.desktop.DesktopApplication;
import ru.kidesoft.desktop.controller.javafx.fxml.AuthController;
import ru.kidesoft.desktop.controller.javafx.fxml.MainController;
import ru.kidesoft.desktop.domain.service.AuthService;
import ru.kidesoft.desktop.domain.service.KktService;

@Controller
public class JavaFxApplication extends Application {
    public ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        context = new SpringApplicationBuilder().sources(DesktopApplication.class).run(args);
        Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
    }

    @Override
    public void stop() throws Exception {
        context.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage) throws Exception {
        context.publishEvent(new StageReadyEvent(stage));
    }
}
