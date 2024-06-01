package ru.kidesoft.desktop.controller.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.kidesoft.desktop.DesktopApplication;

import javax.swing.*;
@Component
public class JavaFxApplication extends Application {

    public ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        context = new SpringApplicationBuilder().sources(DesktopApplication.class).run(args);
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
