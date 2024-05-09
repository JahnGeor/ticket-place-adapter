package ru.kidesoft.ticketplace.client.view.controller.impl;

import javafx.beans.DefaultProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ru.kidesoft.ticketplace.client.main.StageSetting;
import ru.kidesoft.ticketplace.client.view.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController extends Controller {

    public AboutController(Stage stage) {
        super(stage);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logoImage.setImage(StageSetting.logoImage);
        versionLabel.setText("Версия приложения: " + "2.0.0");
        versionLabel.setStyle("-fx-text-fill: #6E62E5");
    }

    @Override
    public <T> void update(T t) {

    }

    @FXML
    private ImageView logoImage;

    @FXML
    private Label versionLabel;
}
