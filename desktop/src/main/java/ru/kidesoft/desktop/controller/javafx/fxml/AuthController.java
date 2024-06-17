package ru.kidesoft.desktop.controller.javafx.fxml;

import atlantafx.base.controls.PasswordTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import net.rgielen.fxweaver.core.FxmlView;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.ApplicationConfiguration;
import ru.kidesoft.desktop.controller.handler.HandlerManager;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.events.StartSessionEvent;
import ru.kidesoft.desktop.controller.javafx.dto.auth.AuthUiDto;
import ru.kidesoft.desktop.controller.javafx.dto.auth.CashierUiDto;
import ru.kidesoft.desktop.domain.dao.database.LoginRepository;
import ru.kidesoft.desktop.domain.dao.kkt.KktOperator;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.profile.Cashier;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.service.AuthService;
import ru.kidesoft.desktop.domain.service.KktService;
import ru.kidesoft.desktop.domain.service.entities.ProfileService;


import java.net.URL;
import java.util.ResourceBundle;
@org.springframework.stereotype.Controller
@FxmlView("/fxml/auth.fxml")
public class AuthController extends Controller<AuthUiDto> {
    private final KktService kktService;
    private final ProfileService profileService;
    private final LoginRepository loginRepository;
    AuthService authService;



    @Autowired
    public AuthController(ConfigurableApplicationContext context, AuthService authService, KktService kktService, ProfileService profileService, LoginRepository loginRepository, LoginRepository loginRepository1) {
        super(context);
        this.authService = authService;
        this.kktService = kktService;
        this.profileService = profileService;
        this.loginRepository = loginRepository1;
    }

    public static StringConverter<Cashier> cashierConverter = new StringConverter<Cashier>() {

        @Override
        public String toString(Cashier cashierDto) {
            if (cashierDto == null) {
                return null;
            } else return String.format("%s : %s", cashierDto.getFullName(), cashierDto.getInn());
        }

        @Override
        public Cashier fromString(String s) {
            return null;
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            var cashierList = profileService.getCashier();
            var loginList = loginRepository.findAll();

            shiftButton.setOnAction(event -> context.getBean(HandlerManager.class).handle(event, this::shiftAction));
            printLastButton.setOnAction(event -> context.getBean(HandlerManager.class).handle(event, this::printLastAction));

            var viewDto = AuthUiDto.builder().login(loginList).cashier(cashierList).build();

            updateView(viewDto);
        } catch (AppException e) {
            throw new RuntimeException(e);
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
    private ComboBox<Cashier> userShiftBox;

    @FXML
    void enterAction(ActionEvent event) throws AppException {

        Login login = Login.builder().email(emailBox.getSelectionModel().getSelectedItem())
                .url(urlBox.getSelectionModel().getSelectedItem())
                .password(passwordField.getPassword()).build();

        authService.login(login);

        context.publishEvent(new StartSessionEvent(StartSessionEvent.StartSession.START));
    }


    void printLastAction(ActionEvent event) throws AppException {
        var cashier = this.userShiftBox.getSelectionModel().getSelectedItem();
        kktService.printXReportByOperator(cashier);
    }


    void shiftAction(ActionEvent event) throws AppException {
        var cashier = this.userShiftBox.getSelectionModel().getSelectedItem();
        kktService.closeShiftByOperator(cashier);
    }

    @Override
    public void updateView(AuthUiDto viewDto) {

        var emailList = FXCollections.observableArrayList(viewDto.getLogin().stream().map(Login::getEmail).distinct().toList());
        emailBox.setItems(emailList);
        emailBox.getSelectionModel().selectFirst();

        var urlList = FXCollections.observableArrayList(viewDto.getLogin().stream().map(Login::getUrl).distinct().toList());

        if (urlList.isEmpty()) {
            urlList.add(context.getBean(ApplicationConfiguration.class).getDefaultUrl());
        }

        urlBox.setItems(urlList);
        urlBox.getSelectionModel().selectFirst();


        userShiftBox.setItems(FXCollections.observableArrayList(viewDto.getCashier().stream().distinct().toList()));
        userShiftBox.setConverter(cashierConverter);
        userShiftBox.getSelectionModel().selectFirst();


        shiftButton.setGraphic(new FontIcon(MaterialDesignP.PRINTER_CHECK));
        enterButton.setGraphic(new FontIcon(FluentUiRegularMZ.PERSON_ARROW_RIGHT_20));
        printLastButton.setGraphic(new FontIcon(FluentUiRegularAL.DOCUMENT_ARROW_LEFT_16));
    }
}
