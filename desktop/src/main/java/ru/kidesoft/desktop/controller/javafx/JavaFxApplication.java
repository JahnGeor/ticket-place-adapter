package ru.kidesoft.desktop.controller.javafx;

import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import ru.kidesoft.desktop.DesktopApplication;
import ru.kidesoft.desktop.controller.javafx.fxml.AuthController;
import ru.kidesoft.desktop.controller.javafx.fxml.MainController;
import ru.kidesoft.desktop.domain.service.AuthService;

@Component
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
        context.getBean(AuthService.class).isActive().ifPresentOrElse(
                uuid -> {
                    context.getBean(StageManager.class).show(MainController.class);
                    System.out.println("active: " + uuid);
                },
                () -> {
                    context.getBean(StageManager.class).show(AuthController.class);
                    System.out.println("no active");
                }
        );
    }
}
