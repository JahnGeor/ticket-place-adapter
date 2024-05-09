package ru.kidesoft.ticketplace.client.view.controller.impl;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.glyphfont.FontAwesome;
import org.kordamp.ikonli.fluentui.*;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import ru.kidesoft.ticketplace.client.view.controller.Controller;
import ru.kidesoft.ticketplace.client.view.controller.ControllerType;
import ru.kidesoft.ticketplace.client.view.controller.Manager;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimerTask;

public class BaseController extends Controller {

    public BaseController(Stage stage) {
        super(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getStage().heightProperty().addListener((obs, oldHeight, newHeight) -> {

            if (Double.isNaN(oldHeight.doubleValue())) {
                return;
            }

            System.out.println("Height changed: " + newHeight);
            System.out.printf("Fixed stage height: %f%n", newHeight.floatValue() - 40.0);
            System.out.println("Scene: " + getStage().getScene().getHeight());
            System.out.println("Border Center: " + ((BorderPane) getStage().getScene().getRoot()).getCenter().getScene().getHeight());
        });



        fileMenu.setGraphic(new FontIcon(FluentUiRegularAL.DOCUMENT_20));
        homeMenuItem.setGraphic(new FontIcon(FluentUiRegularAL.HOME_20));
        settingMenuItem.setGraphic(new FontIcon(FluentUiRegularMZ.SETTINGS_20));
        exitMenuItem.setGraphic(new FontIcon(FluentUiRegularMZ.SHARE_STOP_24));
        logoutMenuItem.setGraphic(new FontIcon(FluentUiRegularMZ.PERSON_ARROW_LEFT_20));

        serviceMenu.setGraphic(new FontIcon(FluentUiRegularAL.GRID_20));
        historyMenuItem.setGraphic(new FontIcon(FluentUiRegularAL.HISTORY_20));
        adminMenuItem.setGraphic(new FontIcon(FluentUiRegularMZ.SHIELD_KEYHOLE_20));
        syncKktMenuItem.setGraphic(new FontIcon(MaterialDesignC.CREDIT_CARD_CLOCK_OUTLINE));


        helpMenu.setGraphic(new FontIcon(FluentUiRegularAL.CHAT_HELP_24));
        updateMenuItem.setGraphic(new FontIcon(FluentUiRegularAL.CLOUD_DOWNLOAD_24));
        aboutMenuItem.setGraphic(new FontIcon(FluentUiRegularMZ.QUESTION_CIRCLE_20));
    }

    @Override
    public <T> void update(T t) {

    }

    @FXML
    private Menu helpMenu;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private MenuItem adminMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private MenuItem homeMenuItem;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem historyMenuItem;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private Menu serviceMenu;

    @FXML
    private MenuItem settingMenuItem;

    @FXML
    private MenuItem updateMenuItem;

    @FXML
    private MenuItem syncKktMenuItem;
    @FXML
    void exitAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void openHome(ActionEvent event) {
        Manager.openScene(ControllerType.MAIN);
    }

    @FXML
    void logoutAction(ActionEvent event) {
        Manager.openScene(ControllerType.AUTH);
    }

    @FXML
    void openAbout(ActionEvent event) {
        Manager.openScene(ControllerType.ABOUT);
    }

    @FXML
    void openAdmin(ActionEvent event) {
        Manager.openScene(ControllerType.ADMIN);
    }

    @FXML
    void openHistory(ActionEvent event) {
        Manager.openScene(ControllerType.HISTORY);
    }

    @FXML
    void openSetting(ActionEvent event) {
        Manager.openScene(ControllerType.SETTING);
    }

    @FXML
    void updateAction(ActionEvent event) {
        Manager.openScene(ControllerType.UPDATE);
    }

    @FXML
    void syncKkt(ActionEvent event) {

    }

}
