package ru.kidesoft.ticketplace.client.domain.interactor.usecase;

import ru.kidesoft.ticketplace.client.domain.dao.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.entities.profile.Profile;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;

import java.util.Optional;

public class ProfileUsecase {
    DatabaseDao databaseDao;

    public ProfileUsecase(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    public Profile getProfile() throws DbException {
        return null;
    }
}
