package ru.kidesoft.desktop.controller.javafx;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import jdk.jfr.TransitionTo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import ru.kidesoft.desktop.controller.javafx.fxml.MainController;

@Component
@AllArgsConstructor
public abstract class Controller<T> implements Initializable {
    protected ConfigurableApplicationContext context;

    public abstract void updateView(T viewDto);
}
