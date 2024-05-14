package ru.kidesoft.ticketplace.client.view.controller.impl;

import atlantafx.base.controls.PasswordTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import ru.kidesoft.ticketplace.client.domain.dao.dto.CashierDto;
import ru.kidesoft.ticketplace.client.domain.dao.dto.LoginProtected;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.Login;
import ru.kidesoft.ticketplace.client.view.controller.Controller;
import ru.kidesoft.ticketplace.client.domain.presenter.ControllerType;
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


        Login login = getUserData();

        var emailList = FXCollections.observableList(login.getEmails());

        var urlList = FXCollections.observableList(login.getUrls());

        var profileList = FXCollections.observableList(login.getCashiers());

        emailBox.setItems(emailList);
        urlBox.setItems(urlList);
        userShiftBox.setItems(profileList);

        emailBox.getSelectionModel().selectFirst();
        urlBox.getSelectionModel().selectFirst();
        userShiftBox.getSelectionModel().selectFirst();
    }

    @Override
    public <T> void update(T t) {

    }

    @FXML
    private ComboBox<String> emailBox;

    @FXML
    private Button enterButton;

    @FXML
    private PasswordTextField passwordField;

    @FXML
    private Button printLastButton;

    @FXML
    private Button shiftButton;

    @FXML
    private ComboBox<String> urlBox;

    @FXML
    private ComboBox<CashierDto> userShiftBox;

    @FXML
    void enterAction(ActionEvent event) {
    }

    @FXML
    void printLastAction(ActionEvent event) {

    }

    @FXML
    void shiftAction(ActionEvent event) {

    }

    Login getUserData() throws IllegalStateException {
        Object o = getStage().getUserData();

        if (o == null) {
            throw new NullPointerException("Данные не переданы, userData = null");
        }

        if (o instanceof Login) {
            return (Login) o;
        } else {
            throw new IllegalStateException(String.format("Неверный тип данных userData: %s", o.getClass().getName()));
        }
    }
}
