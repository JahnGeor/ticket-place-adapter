package ru.kidesoft.ticketplace.client.domain.interactor.usecase;


import ru.kidesoft.ticketplace.client.domain.dao.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.Setting;

public class SettingUsecase {
    DatabaseDao databaseDao;

    public SettingUsecase(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    public Setting getSetting() throws DbException {
        return null;
    }
}
