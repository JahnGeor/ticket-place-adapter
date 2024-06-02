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
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.StageManager;
import ru.kidesoft.desktop.controller.javafx.dto.auth.AuthUiDto;
import ru.kidesoft.desktop.controller.javafx.dto.auth.CashierUiDto;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.service.AuthService;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
@org.springframework.stereotype.Controller
@FxmlView("/fxml/auth.fxml")
public class AuthController extends Controller<AuthUiDto> {

    AuthService authService;



    @Autowired
    public AuthController(ConfigurableApplicationContext context, AuthService authService) {
        super(context);
        this.authService = authService;
    }

    public static StringConverter<CashierUiDto> cashierConverter = new StringConverter<CashierUiDto>() {

        @Override
        public String toString(CashierUiDto cashierDto) {
            if (cashierDto == null) {
                return null;
            } else return String.format("%s : %s", cashierDto.getFullName(), cashierDto.getInn());
        }

        @Override
        public CashierUiDto fromString(String s) {
            return null;
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Login> loginList = authService.findAll();

        var emailList = FXCollections.observableList(loginList.stream().map(Login::getEmail).distinct().toList());
        emailBox.setItems(emailList);
        emailBox.getSelectionModel().selectFirst();



        var urlList = FXCollections.observableList(loginList.stream().map(Login::getUrl).distinct().toList());
        urlBox.setItems(urlList);
        urlBox.getSelectionModel().selectFirst();


        shiftButton.setGraphic(new FontIcon(MaterialDesignP.PRINTER_CHECK));
        enterButton.setGraphic(new FontIcon(FluentUiRegularMZ.PERSON_ARROW_RIGHT_20));
        printLastButton.setGraphic(new FontIcon(FluentUiRegularAL.DOCUMENT_ARROW_LEFT_16));



       // AuthProfile authProfile = getUserData();

//        var emailList = FXCollections.observableList(authProfile.getEmails());
//
//        var urlList = FXCollections.observableList(authProfile.getUrls());
//
//        var profileList = FXCollections.observableList(authProfile.getCashiers());

//        userShiftBox.setConverter(cashierConverter);

//        emailBox.setItems(emailList);
//        urlBox.setItems(urlList);
//        userShiftBox.setItems(profileList);

//        emailBox.getSelectionModel().selectFirst();
//        urlBox.getSelectionModel().selectFirst();
//        userShiftBox.getSelectionModel().selectFirst();
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
    private ComboBox<CashierUiDto> userShiftBox;

    @FXML
    void enterAction(ActionEvent event) {

        Login login = Login.builder().email(emailBox.getSelectionModel().getSelectedItem())
                .url(urlBox.getSelectionModel().getSelectedItem())
                .password(passwordField.getPassword()).build();

        //authService.login(login);

        context.getBean(StageManager.class).show(MainController.class);

//        var login = LoginBuilder.aLogin().withEmail(emailBox.getSelectionModel().getSelectedItem())
//                        .withUrl(urlBox.getSelectionModel().getSelectedItem())
//                                .withPassword(passwordField.getPassword()).build();
//
//        Executor.builder().load().execute(Interactor.getLoginUsecase()::login, login).ifPresent(
//                (result) -> Executor.builder().load().execute(Interactor::openScene, ControllerType.MAIN)
//        );
    }

    @FXML
    void printLastAction(ActionEvent event) {

    }

    @FXML
    void shiftAction(ActionEvent event) {

    }

    @Override
    public void updateView(AuthUiDto viewDto) {

    }
}
