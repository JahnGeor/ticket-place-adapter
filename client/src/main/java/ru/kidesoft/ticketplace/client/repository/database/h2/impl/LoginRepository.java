package ru.kidesoft.ticketplace.client.repository.database.h2.impl;

import ru.kidesoft.ticketplace.client.domain.dao.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.dao.dto.CashierDto;
import ru.kidesoft.ticketplace.client.domain.dao.dto.LoginProtected;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.Login;
import ru.kidesoft.ticketplace.client.repository.database.h2.ConstantNames;
import ru.kidesoft.ticketplace.client.repository.database.h2.TableNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class LoginRepository implements DatabaseDao.LoginDao {
    private Connection connection;

    public LoginRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UUID save(Login login) throws DbException {
        return null;
    }

    @Override
    public Login getLogin() throws DbException {
        String sql = MessageFormat.format("SELECT {0}.FULLNAME, {0}.INN, {1}.EMAIL, {1}.URL " +
                "FROM {2} " +
                "LEFT JOIN {0} ON {0}.LOGIN_ID = {2}.VAL " +
                "LEFT JOIN {1} ON {1}.ID = {2}.VAL WHERE NAME = ?", TableNames.PROFILE.getName(), TableNames.LOGIN.getName(), TableNames.CONSTANT.getName());

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ConstantNames.ACTIVE_LOGIN_UUID.getName());
            var resultSet = statement.executeQuery();

            Login login = new Login();

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

                login.getUrls().add(url);
                login.getEmails().add(email);
                login.getCashiers().add(new CashierDto(fullName, inn));

            }

            return login;
        } catch (Exception e) {
            throw new DbException(e);
        }


    }
}
