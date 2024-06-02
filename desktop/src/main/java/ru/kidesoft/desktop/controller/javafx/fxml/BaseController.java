package ru.kidesoft.desktop.controller.javafx.fxml;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import net.rgielen.fxweaver.core.FxmlView;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.JavaFxApplication;
import ru.kidesoft.desktop.controller.javafx.StageManager;
import ru.kidesoft.desktop.domain.service.AuthService;

import java.net.URL;
import java.util.ResourceBundle;

@org.springframework.stereotype.Controller
@FxmlView("/fxml/base.fxml")
public class BaseController extends Controller<Void> {
    private final AuthService authService;

    @Autowired
    public BaseController(ConfigurableApplicationContext context, AuthService authService) {
        super(context);
        this.authService = authService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        context.getBean(StageManager.class).show(MainController.class);
    }

    @FXML
    void logoutAction(ActionEvent event) {
        //TODO: logout
        authService.logout();
        context.getBean(StageManager.class).show(AuthController.class);
    }

    @FXML
    void openAbout(ActionEvent event) {
        context.getBean(StageManager.class).show(AboutController.class);
    }

    @FXML
    void openAdmin(ActionEvent event) {
        context.getBean(StageManager.class).show(AdminController.class);
    }

    @FXML
    void openHistory(ActionEvent event) {
        context.getBean(StageManager.class).show(HistoryController.class);
    }

    @FXML
    void openSetting(ActionEvent event) {
        context.getBean(StageManager.class).show(SettingController.class);
    }

    @FXML
    void updateAction(ActionEvent event) {
        context.getBean(StageManager.class).show(UpdateController.class);
    }

    @FXML
    void syncKkt(ActionEvent event) {

    }

    @Override
    public void updateView(Void viewDto) {

    }
}
