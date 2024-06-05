package ru.kidesoft.desktop.controller.javafx;

import javafx.fxml.Initializable;
import lombok.AllArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public abstract class Controller<T> implements Initializable {
    protected ConfigurableApplicationContext context;

    public abstract void updateView(T viewDto);
}
