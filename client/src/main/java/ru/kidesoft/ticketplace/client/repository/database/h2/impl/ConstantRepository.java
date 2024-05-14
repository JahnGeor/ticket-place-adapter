package ru.kidesoft.ticketplace.client.repository.database.h2.impl;

import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.repository.database.h2.ConstantNames;
import ru.kidesoft.ticketplace.client.repository.database.h2.TableNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ConstantRepository implements DatabaseDao.ConstantDao {
    private Connection connection;

    public ConstantRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UUID getActiveUserUUID() throws DbException {
        String sql = String.format("SELECT VAL FROM %s WHERE NAME = ?", TableNames.CONSTANT.getName());

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ConstantNames.ACTIVE_LOGIN_UUID.getName());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new DbException("Active user not found");
            }

            return resultSet.getObject(1, UUID.class);
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public void setActiveUserUUID(UUID loginId) throws DbException {
        String sql = String.format("MERGE INTO %s(NAME, VAL) KEY(NAME) VALUES (?, ?)", TableNames.CONSTANT.getName());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, ConstantNames.ACTIVE_LOGIN_UUID.getName());
            preparedStatement.setObject(2, loginId);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public String getDefaultUrl() throws DbException {
        String sql = String.format("SELECT VAL FROM %s WHERE NAME = ?", TableNames.CONSTANT.getName());

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ConstantNames.DEFAULT_URL.getName());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getObject(1, String.class);
            } else {
                throw new DbException("Default url not found");
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }


}
