package ru.kidesoft.ticketplace.client.domain.interactor;

import ru.kidesoft.ticketplace.client.domain.dao.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.presenter.SceneManager;

public final class InteractorBuilder {
    private SceneManager sceneManager;

    private DatabaseDao databaseDao;

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

    public void build() {
        Interactor.setUsecase(databaseDao);
        Interactor.setSceneManager(sceneManager);
    }
}
