package ru.kidesoft.ticketplace.client.view.controller.impl;

import atlantafx.base.controls.PasswordTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import ru.kidesoft.ticketplace.client.domain.dao.database.dto.CashierDto;
import ru.kidesoft.ticketplace.client.domain.executor.Executor;
import ru.kidesoft.ticketplace.client.domain.interactor.Interactor;
import ru.kidesoft.ticketplace.client.domain.models.entities.login.LoginBuilder;
import ru.kidesoft.ticketplace.client.domain.presenter.ControllerType;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.AuthProfile;
import ru.kidesoft.ticketplace.client.view.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController extends Controller {
    public AuthController(Stage stage) {
        super(stage);
    }

    public static StringConverter<CashierDto> cashierConverter = new StringConverter<CashierDto>() {

        @Override
        public String toString(CashierDto cashierDto) {
            if (cashierDto == null) {
                return null;
            } else return String.format("%s : %s", cashierDto.getFullName(), cashierDto.getInn());
        }

        @Override
        public CashierDto fromString(String s) {
            return null;
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shiftButton.setGraphic(new FontIcon(MaterialDesignP.PRINTER_CHECK));
        enterButton.setGraphic(new FontIcon(FluentUiRegularMZ.PERSON_ARROW_RIGHT_20));
        printLastButton.setGraphic(new FontIcon(FluentUiRegularAL.DOCUMENT_ARROW_LEFT_16));


        AuthProfile authProfile = getUserData();

        var emailList = FXCollections.observableList(authProfile.getEmails());

        var urlList = FXCollections.observableList(authProfile.getUrls());

        var profileList = FXCollections.observableList(authProfile.getCashiers());

        userShiftBox.setConverter(cashierConverter);

        emailBox.setItems(emailList);
        urlBox.setItems(urlList);
        userShiftBox.setItems(profileList);

        emailBox.getSelectionModel().selectFirst();
        urlBox.getSelectionModel().selectFirst();
        userShiftBox.getSelectionModel().selectFirst();
    }

    @Override
    public <T> void update(T t) {

        if (t instanceof AuthProfile) {
            getStage().setUserData(t);
        }
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

        var login = LoginBuilder.aLogin().withEmail(emailBox.getSelectionModel().getSelectedItem())
                        .withUrl(urlBox.getSelectionModel().getSelectedItem())
                                .withPassword(passwordField.getPassword()).build();

        Executor.builder().load().execute(Interactor.getLoginUsecase()::login, login).ifPresent(
                (result) -> Executor.builder().load().execute(Interactor::openScene, ControllerType.MAIN)
        );
    }

    @FXML
    void printLastAction(ActionEvent event) {

    }

    @FXML
    void shiftAction(ActionEvent event) {

    }

    AuthProfile getUserData() {
        Object o = getStage().getUserData();

        if (o == null) {
            throw new NullPointerException("Данные не переданы, userData = null");
        }

        if (o instanceof AuthProfile) {
            return (AuthProfile) o;
        } else {
            throw new IllegalStateException(String.format("Неверный тип данных userData: %s", o.getClass().getName()));
        }
    }
}
