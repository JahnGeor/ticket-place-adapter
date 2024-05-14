package ru.kidesoft.ticketplace.client.main;

import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import org.apache.logging.log4j.Logger;
import ru.kidesoft.ticketplace.client.domain.executor.Executor;
import ru.kidesoft.ticketplace.client.domain.interactor.Interactor;
import ru.kidesoft.ticketplace.client.domain.interactor.InteractorBuilder;
import ru.kidesoft.ticketplace.client.domain.presenter.SceneManager;
import ru.kidesoft.ticketplace.client.repository.api.ticketplace.TicketPlaceApiRepository;
import ru.kidesoft.ticketplace.client.repository.database.h2.H2DBRepository;
import ru.kidesoft.ticketplace.client.domain.presenter.ControllerType;
import ru.kidesoft.ticketplace.client.view.controller.Manager;

import java.util.Properties;

public class Main extends Application {
    Logger logger = org.apache.logging.log4j.LogManager.getLogger(Main.class);
    @Override
    public void start(Stage stage) throws Exception {
        logger.trace("Запуск приложения, поиск настроек");
        Properties applicationProperties = new Properties();

        applicationProperties.load(
                Main.class.getResourceAsStream("/ru/kidesoft/ticketplace/application.properties")
        );

        H2DBRepository repo = H2DBRepository.builder()
                .setProperties(applicationProperties).create().migrate().build();

        var ticketPlaceApiRepository = new TicketPlaceApiRepository();

        if (!repo.getMigrateResult().success) {
            logger.fatal("Ошибка в процессе создания миграции репозитория. Приложение будет закрыто", repo.getMigrateResult().exceptionObject);
            StageSetting.exit();
        } else {
            logger.info("Процесс миграции успешно завершен. Текущая версия миграции: {}. Цель миграции: {}", repo.getMigrateResult().initialSchemaVersion, repo.getMigrateResult().targetSchemaVersion);
        }

        setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());

        StageSetting.builder(stage).
                setTitle("Ticket Place").
                setLogo(StageSetting.iconImage).
                setOnClose(Platform::exit);

        SceneManager sceneManager = new Manager().initialize(
                stage,
                ControllerType.MAIN,
                ControllerType.AUTH,
                ControllerType.SETTING,
                ControllerType.ABOUT,
                ControllerType.ADMIN,
                ControllerType.HISTORY,
                ControllerType.UPDATE,
                ControllerType.BASE
        );

        InteractorBuilder.anInteractor().withDatabaseDao(repo).withApiDao(ticketPlaceApiRepository).withSceneManager(sceneManager).build();

        Executor.builder().load().execute(Interactor.getLoginUsecase()::getActiveLoginUUID).ifPresentOrElse(
                login -> Executor.builder().load().execute(
                        Interactor::openScene, ControllerType.MAIN
                ),
                () -> Executor.builder().load().execute(
                        Interactor::openScene, ControllerType.AUTH
                )
        );





    }

    public static void main(String[] args) {
        launch(args);
        Platform.exit();
    }
}
