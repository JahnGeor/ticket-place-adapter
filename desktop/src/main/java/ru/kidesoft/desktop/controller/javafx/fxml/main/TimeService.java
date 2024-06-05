package ru.kidesoft.desktop.controller.javafx.fxml.main;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeService extends Service<Void> {
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");


    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                while (true) {
                    if (isCancelled()) {
                        succeeded();
                    } else {
                        updateMessage(sdf.format(new Date()));
                    }
                }
            }

        };
    }
}

