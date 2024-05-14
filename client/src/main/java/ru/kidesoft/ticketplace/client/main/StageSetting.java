package ru.kidesoft.ticketplace.client.main;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.kidesoft.ticketplace.client.view.controller.Manager;

import java.util.Objects;

public class StageSetting {
    private Stage stage;
    public static final Image iconImage = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/ru/kidesoft/ticketplace/assets/img/icon.png")));
    public static final Image logoImage = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/ru/kidesoft/ticketplace/assets/img/logo.png")));

    public static StageSetting builder(Stage stage) {
        return new StageSetting(stage);
    }

    public StageSetting(Stage stage) {
        this.stage = stage;
    }

    public StageSetting setTitle(String title) {
        stage.setTitle(title);
        return this;
    }

    public StageSetting setLogo(Image image) {
        stage.getIcons().add(image);
        return this;
    }

    public StageSetting setOnClose(Runnable runnable) {
        stage.setOnCloseRequest(event -> {
            runnable.run();
        });

        return this;
    }

    public static void exit() {
        Platform.exit();
    }
}
