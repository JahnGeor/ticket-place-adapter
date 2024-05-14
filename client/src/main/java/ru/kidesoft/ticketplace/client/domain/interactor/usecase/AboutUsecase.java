package ru.kidesoft.ticketplace.client.domain.interactor.usecase;

import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.About;

public class AboutUsecase {
    DatabaseDao databaseDao;

    public AboutUsecase(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    public About getAbout() {
        return null;
    }
}
