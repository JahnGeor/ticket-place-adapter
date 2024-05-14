package ru.kidesoft.ticketplace.client.repository.database.h2.impl;

import ru.kidesoft.ticketplace.client.domain.dao.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.Setting;
import ru.kidesoft.ticketplace.client.repository.database.h2.ConstantNames;
import ru.kidesoft.ticketplace.client.repository.database.h2.TableNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SettingRepository implements DatabaseDao.SettingDao {
    private Connection connection;

    public SettingRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UUID save(Setting setting) throws DbException {
        return null;
    }

    @Override
    public UUID setDefault() throws DbException {
        String sql = String.format("SELECT ID FROM FINAL TABLE(MERGE INTO %s(LOGIN_ID) KEY(LOGIN_ID) VALUES (SELECT VAL FROM %s WHERE NAME = ?))", TableNames.SETTING.getName());
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, ConstantNames.ACTIVE_LOGIN_UUID.getName());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getObject(1, UUID.class);
            } else {
                throw new DbException(new NullPointerException("setting return no uuid"));
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
