package ru.kidesoft.ticketplace.client.view.controller.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import ru.kidesoft.ticketplace.client.view.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController extends Controller {
    public SettingController(Stage stage) {
        super(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autoUpdateLabelBox.setGraphic(new CheckBox());
        KktDriverPathButton.setGraphic(new FontIcon(MaterialDesignF.FOLDER_OPEN));
        printCheckLabel.setGraphic(new CheckBox());
        printTicketLabel.setGraphic(new CheckBox());
        autoReconnectLabel.setGraphic(new CheckBox());
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
    private ComboBox<?> pageOrientationBox;

    @FXML
    private ComboBox<?> pageSizeBox;

    @FXML
    private ComboBox<?> periodBox;

    @FXML
    private Label printCheckLabel;

    @FXML
    private Label printTicketLabel;

    @FXML
    private ComboBox<?> printerNameField;

    @FXML
    private TextField repoPathField;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<?> timeoutBox;

    @FXML
    void KktDriverPathExplorer(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {

    }

}
