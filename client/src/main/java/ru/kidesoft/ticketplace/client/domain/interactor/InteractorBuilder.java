package ru.kidesoft.ticketplace.client.domain.interactor;

import ru.kidesoft.ticketplace.client.domain.dao.api.ApiDao;
import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.presenter.SceneManager;

public final class InteractorBuilder {
    private SceneManager sceneManager;

    private DatabaseDao databaseDao;
    private ApiDao apiDao;

    private InteractorBuilder() {
    }

    public static InteractorBuilder anInteractor() {
        return new InteractorBuilder();
    }

    public InteractorBuilder withSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        return this;
    }

    public InteractorBuilder withDatabaseDao(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
        return this;
    }

    public InteractorBuilder withApiDao(ApiDao apiDao) {
        this.apiDao = apiDao;
        return this;
    }

    public void build() {
        Interactor.setUsecase(databaseDao, apiDao);
        Interactor.setSceneManager(sceneManager);
    }
}
