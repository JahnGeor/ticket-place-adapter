package ru.kidesoft.ticketplace.client.main;

import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.kidesoft.ticketplace.client.view.controller.ControllerType;
import ru.kidesoft.ticketplace.client.view.controller.Manager;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
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
