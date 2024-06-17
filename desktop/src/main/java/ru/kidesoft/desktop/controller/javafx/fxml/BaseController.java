package ru.kidesoft.desktop.controller.javafx.fxml;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.ApplicationConfiguration;
import ru.kidesoft.desktop.controller.handler.Handler;
import ru.kidesoft.desktop.controller.handler.HandlerManager;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.events.StartSessionEvent;
import ru.kidesoft.desktop.controller.javafx.events.manager.StageManager;
import ru.kidesoft.desktop.controller.javafx.fxml.main.MainView;
import ru.kidesoft.desktop.domain.entity.profile.RoleType;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.service.AuthService;
import ru.kidesoft.desktop.domain.service.KktService;
import ru.kidesoft.desktop.domain.service.entities.ProfileService;

import java.net.URL;
import java.util.ResourceBundle;

@org.springframework.stereotype.Controller
@FxmlView("/fxml/base.fxml")
public class BaseController extends Controller<Void> {
    private final KktService kktService;
    private Logger logger = org.apache.logging.log4j.LogManager.getLogger(BaseController.class);

    private final AuthService authService;
    private final ProfileService profileService;

    private HandlerManager handlerManager;

    @Autowired
    public BaseController(ConfigurableApplicationContext context, AuthService authService, HandlerManager handlerManager, ProfileService profileService, KktService kktService) {
        super(context);
        this.authService = authService;
        this.handlerManager = handlerManager;
        this.profileService = profileService;
        this.kktService = kktService;
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
        diagnosticMenuItem.setGraphic(new FontIcon(FluentUiRegularAL.ERROR_CIRCLE_16));

        syncKktMenuItem.setOnAction(event -> handlerManager.handle(event, this::syncKkt));
        diagnosticMenuItem.setOnAction(event -> handlerManager.handle(event, this::diagnosticAction));
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
    private MenuItem diagnosticMenuItem;

    @FXML
    void exitAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void openHome(ActionEvent event) {
        context.getBean(StageManager.class).show(MainView.class);
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
    void openAdmin(ActionEvent event) throws Exception {

        var profile = profileService.getCurrentProfile();

        if (profile.getRole().equals(RoleType.ACCOUNTANT)) {
            context.getBean(StageManager.class).show(AdminController.class);
            logger.info("Запрос на открытие административной панели одобрен");
        } else {
            context.getBean(StageManager.class).showWarningWithPassword("Административная панель", "Доступ запрещен", "Введите пароль администратора")
                    .ifPresent(password -> {
                        if (password.equals(context.getBean(ApplicationConfiguration.class).getPassword())) {
                            logger.info("Запрос на открытие административной панели одобрен");
                            context.getBean(StageManager.class).show(AdminController.class);
                        } else {
                            logger.info("Запрос на открытие административной панели отклонен, неправильный пароль");
                            context.getBean(StageManager.class).showWarning("Административная панель", "Неправильный пароль администратора");
                        }
                    });

            logger.info("Запрос на открытие административной панели отклонен, роль {} не является административной", profile.getRole());
        }


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


    void syncKkt(ActionEvent event) throws AppException {
        kktService.kktTime();
    }

    @Override
    public void updateView(Void viewDto) {

    }

    public void diagnosticAction(ActionEvent event) {
        context.publishEvent(new StartSessionEvent(StartSessionEvent.StartSession.START));
    }
}
