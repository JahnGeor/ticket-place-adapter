package ru.kidesoft.desktop.controller.javafx.events.manager;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.events.StageReadyEvent;
import ru.kidesoft.desktop.controller.javafx.events.StartSessionEvent;
import ru.kidesoft.desktop.controller.javafx.fxml.AuthController;
import ru.kidesoft.desktop.controller.javafx.fxml.BaseController;
import ru.kidesoft.desktop.domain.exception.ApiException;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.DbException;
import ru.kidesoft.desktop.domain.exception.KktException;
import ru.kidesoft.desktop.domain.service.AuthService;

import java.io.IOException;

@Component
public class StageManager implements ApplicationListener<StageReadyEvent> {
    private ConfigurableApplicationContext context;
    private final FxWeaver fxWeaver;
    private Stage stage;
    @Value("${spring.application.title}")
    private String title;

    @Value("${spring.application.icon}")
    private Resource logoImage;

    @Autowired
    public StageManager(
            FxWeaver fxWeaver,
            ConfigurableApplicationContext context) {
        this.context = context;
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        stage = event.getStage();
        Parent parent = fxWeaver.loadView(BaseController.class);
        Scene scene = new Scene(parent, 600, 415);
        stage.setScene(scene);
        stage.sizeToScene();

        try {
            stage.getIcons().add(new Image(logoImage.getURL().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle(title);
        stage.show();

        context.getBean(AuthService.class).getActiveLogin().ifPresentOrElse(
                currentLogin -> context.publishEvent(new StartSessionEvent(StartSessionEvent.StartSession.START))
                , () -> {
                    context.getBean(StageManager.class).show(AuthController.class);
                }
        );
    }


    public void show(Class<? extends Controller> controller) {
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        MenuBar top = (MenuBar) root.getTop();

        if (controller == AuthController.class) {
            top.setDisable(true);
            top.setVisible(false);
        } else {
            top.setDisable(false);
            top.setVisible(true);
        }

        Parent parent = fxWeaver.loadView(controller);

        StackPane center = (StackPane) root.getCenter();
        center.getChildren().clear();
        center.getChildren().add(parent);
    }

    public void showError(AppException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка", ButtonType.OK);

        alert.setTitle("Ошибка");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText(e.getMessage());

        try {
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(logoImage.getURL().toString()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        if (e instanceof DbException) {
            alert.setHeaderText("Внутренняя ошибка базы данных");
        } else if(e instanceof KktException) {
            alert.setHeaderText("Внутренняя ошибка ККТ");
        } else if (e instanceof ApiException) {
            alert.setHeaderText("Ошибка обращения к удаленному серверу");
        } else {
            alert.setHeaderText("Неизвестная ошибка");
        }

        alert.showAndWait();
    }
}
