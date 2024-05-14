package ru.kidesoft.ticketplace.client.view.controller.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import ru.kidesoft.ticketplace.client.domain.models.entities.setting.Setting;
import ru.kidesoft.ticketplace.client.domain.models.entities.setting.enums.PageOrientation;
import ru.kidesoft.ticketplace.client.domain.models.entities.setting.enums.PageSize;
import ru.kidesoft.ticketplace.client.view.controller.Controller;

import java.net.URL;
import java.time.Duration;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingController extends Controller {
    public SettingController(Stage stage) {
        super(stage);
    }

    public Setting getUserData() {
        Object o = getStage().getUserData();

        if (o == null) {
            throw new NullPointerException("user data object is null");
        }

        if (!(o instanceof Setting)) {
            throw new ClassCastException("user data object is not " + Setting.class.getName());
        }

        return (Setting) o;
    }

    public void setUserData(Setting setting) {
        KktDriverPathField.setText(setting.getKkt().getKktDriverPath());
        repoPathField.setText(setting.getUpdate().getRepository());

        printerNameField.getItems().addAll(
                Printer.getAllPrinters().stream().map(Printer::getName).toList()
        );

        printerNameField.setValue(setting.getPrinter().getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autoUpdateLabelBox.setGraphic(new CheckBox());
        KktDriverPathButton.setGraphic(new FontIcon(MaterialDesignF.FOLDER_OPEN));
        printCheckLabel.setGraphic(new CheckBox());
        printTicketLabel.setGraphic(new CheckBox());
        autoReconnectLabel.setGraphic(new CheckBox());


        setUserData(getUserData());

    }

    @Override
    public <T> void update(T t) {

    }

    @FXML
    private Label autoReconnectLabel;

    @FXML
    private Button KktDriverPathButton;

    @FXML
    private TextField KktDriverPathField;

    @FXML
    private Label KktPathLabel;

    @FXML
    private Label autoUpdateLabelBox;

    @FXML
    private ComboBox<PageOrientation> pageOrientationBox;

    @FXML
    private ComboBox<PageSize> pageSizeBox;

    @FXML
    private ComboBox<Duration> periodBox;

    @FXML
    private Label printCheckLabel;

    @FXML
    private Label printTicketLabel;

    @FXML
    private ComboBox<String> printerNameField;

    @FXML
    private TextField repoPathField;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<Duration> timeoutBox;

    @FXML
    void KktDriverPathExplorer(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {

    }

}
