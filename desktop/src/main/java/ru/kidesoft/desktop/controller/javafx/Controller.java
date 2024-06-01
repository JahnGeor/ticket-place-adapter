package ru.kidesoft.desktop.controller.javafx;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import jdk.jfr.TransitionTo;

public abstract class Controller implements Initializable {
    Stage stage;



    public static class Transition {
        Runnable animate;
        Node next;
        Node previous;

        public static Transition builder() {
            return new Transition();
        }

        public Transition animate(Runnable animate) {
            this.animate = animate;
            return this;
        }

        public Transition next(Node next) {
            this.next = next;
            return this;
        }

        public Transition previous(Node previous) {
            this.previous = previous;
            return this;
        }

        public void transition() {
            animate.run();
        }
    }
}
