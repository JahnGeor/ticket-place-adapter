package ru.kidesoft.desktop;

import javafx.application.Application;
import javafx.application.Platform;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.kidesoft.desktop.controller.javafx.JavaFxApplication;
import ru.kidesoft.desktop.utils.Lock;

import java.nio.file.Path;

@SpringBootApplication
public class DesktopApplication {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {


        try {
        if (Lock.tryLock()) {
            logger.warn("Попытка запустить несколько экземпляров приложения");
            Platform.exit();
        }
        } catch (Exception e) {
            logger.error(e);
        }

        logger.info("Application start");

        Application.launch(JavaFxApplication.class, args);
    }
    @Bean
    public FxWeaver fxWeaver(ConfigurableApplicationContext context) {
        return new SpringFxWeaver(context);
    }
}


