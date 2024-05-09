package ru.kidesoft.ticketplace.client.view.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ru.kidesoft.ticketplace.client.view.controller.impl.BaseController;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private static Stage primaryStage;
    private static List<Controller> controllers = new ArrayList<>();
    public static Class<? extends Controller> activeControllerClass = BaseController.class;

    public static void initialize(Stage stage, ControllerType... controllerType) throws Exception {
        Manager.primaryStage = stage;


        for (ControllerType type : controllerType) {
            Controller controller = type.getControllerClass().getConstructor(Stage.class).newInstance(stage);
            controllers.add(controller);
        }

        var baseLoader = ControllerType.BASE.getLoader();

        baseLoader.setController(Manager.getController(ControllerType.BASE));

        var scene = new Scene(baseLoader.load());

        Manager.primaryStage.setHeight(415);

        stage.setScene(scene);
        stage.sizeToScene();
        activeControllerClass = ControllerType.BASE.getControllerClass();
    }

    public static void addController(Controller controller) {
        controllers.add(controller);
    }

    public static void removeController(Controller controller) {
        controllers.remove(controller);
    }

    public static Controller getController(ControllerType type) throws NullPointerException {
        for (Controller controller : controllers) {
            if (controller.getClass() == type.getControllerClass()) {
                return controller;
            }
        }
        throw new NullPointerException("Controller not found");
    }

    static void animate(Stage stage, Node previous, Node next) {
        BorderPane borderPane = (BorderPane) stage.getScene().getRoot();
        var borderPaneCenter = (StackPane) borderPane.getCenter();

        var centerChildren = borderPaneCenter.getChildren();


        centerChildren.add(next);

        next.translateXProperty().set(stage.getScene().getWidth());

        KeyValue kvPrev = new KeyValue(previous.translateXProperty(), -stage.getScene().getWidth(), Interpolator.EASE_BOTH);
        KeyFrame kfPrev = new KeyFrame(Duration.seconds(0.5), kvPrev);

        KeyValue kvNext = new KeyValue(next.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kfNext = new KeyFrame(Duration.seconds(0.5), kvNext);

        playAnimation(previous, next, centerChildren, kfPrev, kfNext);
    }

    private static void playAnimation(Node previous, Node next, ObservableList<Node> centerChildren, KeyFrame kfPrev, KeyFrame kfNext) {
        Timeline timeline = new Timeline(kfNext, kfPrev);

        timeline.setOnFinished(e -> {
            next.setDisable(false);
            centerChildren.remove(previous);

            if (centerChildren.size() > 1) {
                centerChildren.removeIf(node -> !node.equals(next));
            }
        });

        timeline.play();

    }

    static void animateRefresh(Stage stage, Node previous, Node next) {
        next.setDisable(false);

        var centerChildren = ((StackPane) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren();

        centerChildren.add(next);
        next.opacityProperty().set(0);

        KeyValue kvPrev = new KeyValue(previous.opacityProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame kfPrev = new KeyFrame(Duration.seconds(0.25), kvPrev);

        KeyValue kvNext = new KeyValue(next.opacityProperty(), 1, Interpolator.EASE_IN);
        KeyFrame kfNext = new KeyFrame(Duration.seconds(1), kvNext);

        playAnimation(previous, next, centerChildren, kfNext, kfPrev);
    }

    public static void openScene(ControllerType type) {

        try {

            Controller controller = getController(type);

            BorderPane borderPane = (BorderPane) primaryStage.getScene().getRoot();

            StackPane center = (StackPane) borderPane.getCenter();

            FXMLLoader loader = type.getLoader();

            loader.setController(controller);

            Node nextPane = loader.load();

            nextPane.setDisable(true);

            ObservableList<Node> centerChildren = center.getChildren();

            if (centerChildren.isEmpty()) {
                centerChildren.add(nextPane);
                nextPane.setDisable(false);
                primaryStage.show();
            } else {

                Node previous = centerChildren.get(0);

                if (activeControllerClass == type.getControllerClass()) {
                    animateRefresh(primaryStage, centerChildren.get(0), nextPane);
                } else {
                    animate(primaryStage, previous, nextPane);
                }
            }

            activeControllerClass = type.getControllerClass();

            if (type == ControllerType.AUTH) {
                borderPane.getTop().setDisable(true);
                borderPane.getTop().setVisible(false);
                borderPane.getTop().setManaged(false);
            } else {
                borderPane.getTop().setDisable(false);
                borderPane.getTop().setVisible(true);
                borderPane.getTop().setManaged(true);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
