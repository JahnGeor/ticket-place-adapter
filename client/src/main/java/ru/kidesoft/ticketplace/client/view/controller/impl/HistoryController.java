package ru.kidesoft.ticketplace.client.view.controller.impl;

import atlantafx.base.layout.InputGroup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.kidesoft.ticketplace.client.view.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController extends Controller {
    @FXML
    private Label printCheckLabel;
    @FXML
    private Label printTicketLabel;

    @FXML
    private void printAction(ActionEvent event) {

    }
    public HistoryController(Stage stage) {
        super(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Label("", new CheckBox());

        printCheckLabel.setGraphic(new CheckBox());
        printTicketLabel.setGraphic(new CheckBox());
    }

    @Override
    public <T> void update(T t) {

    }
}
