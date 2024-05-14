package ru.kidesoft.ticketplace.client.repository.database.h2.impl;

import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.entities.session.Session;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.repository.database.h2.ConstantNames;
import ru.kidesoft.ticketplace.client.repository.database.h2.TableNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SessionRepository implements DatabaseDao.SessionDao {
    private Connection connection;

    public SessionRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UUID save(Session session) throws DbException {
        String sql = String.format("SELECT ID FROM FINAL TABLE(" +
                "MERGE INTO %s" +
                "(LOGIN_ID, CREATED_AT, EXPIRED_AT, TOKEN_TYPE, ACCESS_TOKEN) KEY(LOGIN_ID) VALUES (SELECT VAL FROM %s WHERE NAME = ?, ?, ?, ?, ?))", TableNames.SESSION, TableNames.CONSTANT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ConstantNames.ACTIVE_LOGIN_UUID.getName());
            preparedStatement.setObject(2, session.getCreatedAt());
            preparedStatement.setObject(3, session.getExpiredAt());
            preparedStatement.setString(4, session.getTokenData().getTokenType());
            preparedStatement.setString(5, session.getTokenData().getAccessToken());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getObject("ID", UUID.class);
            } else {
                throw new DbException("Не удалось получить идентификатор сессии");
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
