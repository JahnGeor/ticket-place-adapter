package ru.kidesoft.ticketplace.client.repository.database.h2.impl;

import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.entities.profile.Profile;
import ru.kidesoft.ticketplace.client.domain.models.entities.profile.ProfileBuilder;
import ru.kidesoft.ticketplace.client.domain.models.entities.profile.enums.RoleType;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.repository.database.h2.ConstantNames;
import ru.kidesoft.ticketplace.client.repository.database.h2.TableNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProfileRepository implements DatabaseDao.ProfileDao {
    private Connection connection;

    public ProfileRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UUID save(Profile profile) throws DbException {
        String sql = String.format(
                "SELECT ID FROM FINAL TABLE(MERGE INTO %s(LOGIN_ID, " +
                        "INN, " +
                        "ROLE, " +
                        "AVATAR, " +
                        "FULLNAME, " +
                        "USERNAME, " +
                        "USER_ID) KEY(LOGIN_ID) VALUES(SELECT VAL FROM %s WHERE NAME = ?, ?, ?, ?, ?, ?, ?))", TableNames.PROFILE.getName(), TableNames.CONSTANT.getName()
        );

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, ConstantNames.ACTIVE_LOGIN_UUID.getName());
            preparedStatement.setLong(2, profile.getInn());
            preparedStatement.setInt(3, profile.getRole().getId());
            preparedStatement.setString(4, profile.getAvatar());
            preparedStatement.setString(5, profile.getFullName());
            preparedStatement.setString(6, profile.getUserName());
            preparedStatement.setInt(7, profile.getUserId());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getObject("ID", UUID.class);
            } else {
                throw new SQLException("Не удалось получить идентификатор профиля");
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public Profile getProfile() throws DbException {
        String sql = String.format("SELECT * FROM %s WHERE LOGIN_ID = (SELECT VAL FROM %s WHERE NAME = ?)", TableNames.PROFILE.getName(), TableNames.CONSTANT.getName());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, ConstantNames.ACTIVE_LOGIN_UUID.getName());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {

                return ProfileBuilder.aProfile()
                        .withFullName(rs.getString("FULLNAME"))
                        .withAvatar(rs.getString("AVATAR"))
                        .withInn(rs.getLong("INN"))
                        .withRole(RoleType.getById(rs.getInt("ROLE")))
                        .withUserName(rs.getString("USERNAME"))
                        .withUserId(rs.getInt("USER_ID"))
                        .build();
            } else {
                throw new SQLException("Не удалось получить профиль");
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
