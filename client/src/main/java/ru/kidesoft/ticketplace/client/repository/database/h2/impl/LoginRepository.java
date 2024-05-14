package ru.kidesoft.ticketplace.client.repository.database.h2.impl;

import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.dao.database.dto.CashierDto;
import ru.kidesoft.ticketplace.client.domain.models.entities.login.Login;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.AuthProfile;
import ru.kidesoft.ticketplace.client.repository.database.h2.ConstantNames;
import ru.kidesoft.ticketplace.client.repository.database.h2.TableNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.UUID;

public class LoginRepository implements DatabaseDao.LoginDao {
    private Connection connection;

    public LoginRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UUID save(Login login) throws DbException {
        String sql = String.format("SELECT ID FROM FINAL TABLE(" +
                " MERGE INTO" +
                " %s(EMAIL, PASSWORD, URL)" +
                " KEY(EMAIL, URL) VALUES (?, ?, ?))", TableNames.LOGIN.getName());

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login.getEmail());
            preparedStatement.setString(2, login.getPassword());
            preparedStatement.setString(3, login.getUrl());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getObject("ID", UUID.class);
            } else {
                throw new SQLException("No ID returned from database");
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }

    }

    @Override
    public AuthProfile getAuthProfile() throws DbException {
        String sql = MessageFormat.format(
                "SELECT {0}.FULLNAME, {0}.INN, {1}.EMAIL, {1}.URL " +
                "FROM {1} " +
                "LEFT JOIN {0} " +
                "ON {1}.ID = {0}.LOGIN_ID", TableNames.PROFILE.getName(), TableNames.LOGIN.getName());

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            var resultSet = statement.executeQuery();

            AuthProfile authProfile = new AuthProfile();

            while (resultSet.next()) {


                String fullName = resultSet.getString(1);

                if (resultSet.wasNull()) {
                    continue;
                }

                Long inn = resultSet.getLong(2);

                if (resultSet.wasNull()) {
                    continue;
                }

                String email = resultSet.getString(3);

                if (resultSet.wasNull()) {
                    continue;
                }
                String url = resultSet.getString(4);

                if (resultSet.wasNull()) {
                    continue;
                }

                authProfile.getUrls().add(url);
                authProfile.getEmails().add(email);
                authProfile.getCashiers().add(new CashierDto(fullName, inn));

            }

            return authProfile;
        } catch (Exception e) {
            throw new DbException(e);
        }


    }
}
