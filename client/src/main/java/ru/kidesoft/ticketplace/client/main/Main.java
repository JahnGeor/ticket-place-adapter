package ru.kidesoft.ticketplace.client.main;

import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import org.apache.logging.log4j.Logger;
import ru.kidesoft.ticketplace.client.repository.database.h2.H2DBRepository;
import ru.kidesoft.ticketplace.client.view.controller.ControllerType;
import ru.kidesoft.ticketplace.client.view.controller.Manager;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
public class Main extends Application {
    Logger logger = org.apache.logging.log4j.LogManager.getLogger(Main.class);
    @Override
    public void start(Stage stage) throws Exception {
        logger.error("Application started");

        setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());

        StageSetting.builder(stage).
                setTitle("Ticket Place").
                setLogo(StageSetting.iconImage).
                setOnClose(Platform::exit);

        Manager.initialize(stage,
                ControllerType.MAIN,
                ControllerType.AUTH,
                ControllerType.SETTING,
                ControllerType.ABOUT,
                ControllerType.ADMIN,
                ControllerType.HISTORY,
                ControllerType.UPDATE,
                ControllerType.BASE);

        Manager.openScene(ControllerType.MAIN);
    }

    public static void main(String[] args) {
        launch(args);
        Platform.exit();
    }
}
