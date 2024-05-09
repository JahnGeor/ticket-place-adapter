package ru.kidesoft.ticketplace.client.view.controller;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.kidesoft.ticketplace.client.domain.presenter.Presenter;

public abstract class Controller implements Initializable, Presenter {
    private Stage stage;
    private Scene mainScene;

    public Controller(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }
}
