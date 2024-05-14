package ru.kidesoft.ticketplace.client.repository.database.h2.impl;

import ru.kidesoft.ticketplace.client.domain.dao.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.entities.profile.Profile;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;

import java.sql.Connection;
import java.util.UUID;

public class ProfileRepository implements DatabaseDao.ProfileDao {
    private Connection connection;

    public ProfileRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UUID save(Profile profile) throws DbException {
        return null;
    }
}
