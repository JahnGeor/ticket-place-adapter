package ru.kidesoft.ticketplace.client.repository.database.h2.impl;

import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;

import java.sql.Connection;

public class ClickRepository implements DatabaseDao.ClickDao {
    private Connection connection;
    public ClickRepository(Connection connection) {
        this.connection = connection;
    }
}
