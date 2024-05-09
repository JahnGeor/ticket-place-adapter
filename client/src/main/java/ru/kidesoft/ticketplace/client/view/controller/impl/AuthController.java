package ru.kidesoft.ticketplace.client.view.controller.impl;

import atlantafx.base.controls.PasswordTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import ru.kidesoft.ticketplace.client.view.controller.Controller;
import ru.kidesoft.ticketplace.client.view.controller.ControllerType;
import ru.kidesoft.ticketplace.client.view.controller.Manager;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController extends Controller {
    public AuthController(Stage stage) {
        super(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shiftButton.setGraphic(new FontIcon(MaterialDesignP.PRINTER_CHECK));
        enterButton.setGraphic(new FontIcon(FluentUiRegularMZ.PERSON_ARROW_RIGHT_20));
        printLastButton.setGraphic(new FontIcon(FluentUiRegularAL.DOCUMENT_ARROW_LEFT_16));

    }

    @Override
    public <T> void update(T t) {

    }

    @FXML
    private ComboBox<?> emailBox;

    @FXML
    private Button enterButton;

    @FXML
    private PasswordTextField passwordField;

    @FXML
    private Button printLastButton;

    @FXML
    private Button shiftButton;

    @FXML
    private ComboBox<?> urlBox;

    @FXML
    private ComboBox<?> userShiftBox;

    @FXML
    void enterAction(ActionEvent event) {
        Manager.openScene(ControllerType.MAIN);
    }

    @FXML
    void printLastAction(ActionEvent event) {

    }

    @FXML
    void shiftAction(ActionEvent event) {

    }
}
