package ru.kidesoft.ticketplace.client.view.controller;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import ru.kidesoft.ticketplace.client.domain.presenter.Presenter;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class Controller implements Initializable, Presenter {
    private Stage stage;
    private Scene mainScene;

    public Controller(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    @Override
    public void sendAlert(String header, String message, Exception exception) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception Dialog");
            alert.setHeaderText(header);
            alert.setContentText(message);
            alert.setTitle("Произошла ошибка");
            var stringWriter = new StringWriter();
            var printWriter = new PrintWriter(stringWriter);
            exception.printStackTrace(printWriter);

            var textArea = new TextArea(stringWriter.toString());
            textArea.setEditable(false);
            textArea.setWrapText(false);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            var content = new GridPane();
            content.setMaxWidth(Double.MAX_VALUE);
            content.add(new Label("Полная ветка исключения:"), 0, 0);
            content.add(textArea, 0, 1);


            Platform.runLater(() -> {
                final Hyperlink detailsButton = ( Hyperlink ) alert.getDialogPane().lookup( ".details-button" );
                detailsButton.setText("Показать ошибку");
                alert.getDialogPane().expandedProperty().addListener(
                        (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue ) ->
                        {
                            detailsButton.setText( newValue ? "Скрыть ошибку" : "Показать ошибку" );
                        });
            });

            alert.getDialogPane().setExpandableContent(content);
            alert.initOwner(getStage());
            alert.showAndWait();
    }
}
