package ru.kidesoft.desktop.controller.javafx.fxml.main;

import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import ru.kidesoft.desktop.controller.javafx.events.manager.StageManager;
import ru.kidesoft.desktop.infrastructure.service.PrinterService;

@Component
public class ListenerService extends ScheduledService<Integer> {
    ConfigurableApplicationContext context;
    public static final Logger logger = LogManager.getLogger(ListenerService.class);

    PrinterService printerService;
    private int errorCounter = 0;
    private int maxExceptionsCount = 3;

    @Autowired
    ListenerService( PrinterService printerService, ConfigurableApplicationContext context) {
        super();

        this.context = context;
        this.printerService = printerService;
        setOnCancelled(event ->
        {
            logger.trace("Listener service cancelled");
            Platform.runLater(
                    () -> {
                        context.getBean(StageManager.class).showNotification("Сервис прослушивания удаленного сервера", "Сервис прослушивания удаленного сервера остановлен");
                    }
            );
        }
        );

        setOnFailed(event -> {
            logger.trace("Listener service failed {}/{}", errorCounter + 1, maxExceptionsCount);
            errorCounter += 1;

            if (errorCounter >= maxExceptionsCount) {
                Platform.runLater(super::cancel);
                logger.trace("Listener service force cancel by max error counter");
                errorCounter = 0;
            }
        });


        setOnSucceeded(event -> {
            errorCounter = 0;
        });
    }

    @Override
    protected Task<Integer> createTask() {
        return new ListenerTask(printerService);
    }
}
@Component
class ListenerTask extends Task<Integer> {
    public static final Logger logger = LogManager.getLogger(ListenerService.class);

    PrinterService printerService;

    public ListenerTask(PrinterService printerService) {
        this.printerService = printerService;
    }

    @Override
    protected Integer call() throws Exception {



        // TODO: ServiceManager.withHandler().withTries().returnException().execute(profileService::getByLogin);
        // TODO: ServiceManager.noHandler().withTries(6).returnException().execute(profileService::getCurrentProfile); -> выполняет метод 6 раз (пропуская все Exception), возвращает значение метода или его финальный exception, без обработки в Handler

        printerService.printByClick();

        logger.trace("Listener task executed");

        return 0;
    }
}