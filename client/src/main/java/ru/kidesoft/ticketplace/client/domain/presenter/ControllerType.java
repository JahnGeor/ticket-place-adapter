package ru.kidesoft.ticketplace.client.domain.presenter;

import javafx.fxml.FXMLLoader;
import ru.kidesoft.ticketplace.client.view.controller.Controller;
import ru.kidesoft.ticketplace.client.view.controller.impl.*;

import java.net.URL;

public enum ControllerType {
    BASE("base.fxml", BaseController.class),
    AUTH("auth.fxml", AuthController.class),
    ABOUT("about.fxml", AboutController.class),
    ADMIN("admin.fxml", AdminController.class),
    MAIN("main.fxml", MainController.class),
    HISTORY("history.fxml", HistoryController.class),
    UPDATE("update.fxml", UpdateController.class),
    SETTING("setting.fxml", SettingController.class);

    private final String fxmlPath;
    private final Class<? extends Controller> controller;

    ControllerType(String fxmlPath, Class<? extends Controller> controller) {
        this.fxmlPath = fxmlPath;
        this.controller = controller;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public URL getFxmlUrl() {
        return getControllerClass().getResource(fxmlPath);
    }

    public FXMLLoader getLoader() {
        return new FXMLLoader(getFxmlUrl());
    }

    public Class<? extends Controller> getControllerClass() {
        return controller;
    }


}
