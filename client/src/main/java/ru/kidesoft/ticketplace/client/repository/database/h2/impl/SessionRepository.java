package ru.kidesoft.ticketplace.client.repository.database.h2.impl;

import ru.kidesoft.ticketplace.client.domain.dao.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.entities.session.Session;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;

import java.sql.Connection;
import java.util.UUID;

public class SessionRepository implements DatabaseDao.SessionDao {
    private Connection connection;

    public SessionRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UUID save(Session session) throws DbException {
        return null;
    }
}
