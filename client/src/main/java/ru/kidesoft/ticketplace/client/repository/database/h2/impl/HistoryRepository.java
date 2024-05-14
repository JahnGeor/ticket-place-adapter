package ru.kidesoft.ticketplace.client.repository.database.h2.impl;

import ru.kidesoft.ticketplace.client.domain.dao.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.entities.history.History;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;

import java.sql.Connection;
import java.util.UUID;

public class HistoryRepository implements DatabaseDao.HistoryDao {
    private Connection connection;

    public HistoryRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UUID save(History history) throws DbException {
        return null;
    }
}
