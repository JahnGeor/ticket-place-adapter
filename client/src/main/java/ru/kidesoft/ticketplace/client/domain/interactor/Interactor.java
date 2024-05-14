package ru.kidesoft.ticketplace.client.domain.interactor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kidesoft.ticketplace.client.domain.dao.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.interactor.usecase.*;
import ru.kidesoft.ticketplace.client.domain.models.exception.ControllerException;
import ru.kidesoft.ticketplace.client.domain.presenter.ControllerType;
import ru.kidesoft.ticketplace.client.domain.presenter.SceneManager;

import static org.kordamp.ikonli.materialdesign2.MaterialDesignL.LOGIN;

public class Interactor  {
    private static final Logger logger = LogManager.getLogger();
    private static SceneManager sceneManager;
    private static LoginUsecase loginUsecase;
    private static ProfileUsecase profileUsecase;
    private static HistoryUsecase historyUsecase;
    private static AboutUsecase aboutUsecase;
    private static SettingUsecase settingUsecase;

    public static SceneManager getSceneManager() {
        return sceneManager;
    }

    public static ProfileUsecase getProfileUsecase() {
        return profileUsecase;
    }

    public static HistoryUsecase getHistoryUsecase() {
        return historyUsecase;
    }

    public static AboutUsecase getAboutUsecase() {
        return aboutUsecase;
    }

    public static SettingUsecase getSettingUsecase() {
        return settingUsecase;
    }

    public static LoginUsecase getLoginUsecase() {
        return loginUsecase;
    }

    public Interactor() {}

    public static void setUsecase(DatabaseDao databaseDao) {
        loginUsecase = new LoginUsecase(databaseDao);
        profileUsecase = new ProfileUsecase(databaseDao);
        historyUsecase = new HistoryUsecase(databaseDao);
        aboutUsecase = new AboutUsecase(databaseDao);
        settingUsecase = new SettingUsecase(databaseDao);
    }

    public static void setSceneManager(SceneManager sceneManager) {
        Interactor.sceneManager = sceneManager;
    }

    public static void openScene(ControllerType type) throws ControllerException {

        // TODO: Добавить методы получения данных от типа контроллера

        // TODO: switch (type) case MAIN: setUserData(ProfileUsecase::getProfile); break;
        try {
            switch (type) {
                case AUTH:
                    sceneManager.setUserData(loginUsecase::getLogin);
                    break;
                case MAIN:
                    sceneManager.setUserData(profileUsecase::getProfile);
                    break;
                case ABOUT:
                    sceneManager.setUserData(aboutUsecase::getAbout);
                    break;
                case HISTORY:
                    sceneManager.setUserData(historyUsecase::getHistory);
                    break;
                case SETTING:
                    sceneManager.setUserData(settingUsecase::getSetting);
                    break;
            }

            sceneManager.openScene(type);

            logger.trace("Сцена с типом {} открыта", type);
        } catch (Exception e) {
            throw new ControllerException(e);
        }
    }
}
