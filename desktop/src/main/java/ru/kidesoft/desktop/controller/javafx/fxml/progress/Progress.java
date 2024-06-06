package ru.kidesoft.desktop.controller.javafx.fxml.progress;

import atlantafx.base.theme.Styles;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import ru.kidesoft.desktop.ApplicationConfiguration;

@Component
public class Progress {

    private ConfigurableApplicationContext context;

    private Stage stage;
    private Node parent;
    private ProgressBar progressBar;

    private Label title = new Label();
    private Label progressText = new Label();
    private Label value = new Label();

    private StackPane progressStackPane;

    //private Label result;
    private Button button = new Button("Закрыть");
    //private Label title;


    @Autowired
    public Progress(ConfigurableApplicationContext context) {
        this.context = context;

        Platform.runLater(
                () -> {
                    VBox vBox = new VBox();
                    progressBar = new ProgressBar();
                    progressBar.getStyleClass().add(Styles.LARGE);

                    progressStackPane = new StackPane(progressBar, progressText);


                    progressBar.setPrefSize(280, 30);


                    button.setOnAction(e -> stage.close());


                    vBox.setAlignment(Pos.CENTER);
                    vBox.setSpacing(10);
                    vBox.setFillWidth(true);

                    vBox.getChildren().addAll(title, progressStackPane, value, button);

                    button.setVisible(false);

                    stage = new Stage();
                    stage.setAlwaysOnTop(true);
                    stage.setResizable(false);
                    stage.centerOnScreen();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(vBox,300, 150));

                    stage.setTitle(context.getBean(ApplicationConfiguration.class).getTitle());
                    stage.getIcons().add(context.getBean(ApplicationConfiguration.class).getIcon());
                }
        );


//        try {
//            stage.getIcons().add(new Image(resource.getURL().toExternalForm()));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

    }

    public Progress bindProgressProperty(ObservableValue<? extends Number> property) {
        progressBar.progressProperty().bind(property);
        return this;
    }

    public Progress bindMessageProperty(ObservableValue<? extends String> property) {
        progressText.textProperty().bind(property);
        return this;
    }

    public Progress bindTitleProperty (ObservableValue<? extends String> property) {
        title.textProperty().bind(property);
        return this;
    }

    public Progress bindValueProperty(ObservableValue<? extends String> property) {
        value.textProperty().bind(property);
        return this;
    }

    public void showButton() {
        button.setVisible(true);
    }

    public void close() {
        stage.close();
    }

    public void start() {
        stage.showAndWait();
    }

}
