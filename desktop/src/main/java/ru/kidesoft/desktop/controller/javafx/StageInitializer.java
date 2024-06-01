package ru.kidesoft.desktop.controller.javafx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.kidesoft.desktop.controller.javafx.fxml.BaseController;
import ru.kidesoft.desktop.controller.javafx.fxml.MainController;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final FxWeaver fxWeaver;

    @Autowired
    public StageInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        Parent parent = fxWeaver.loadView(BaseController.class);
        Scene scene = new Scene(parent, 600, 300);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
