package ru.kidesoft.ticketplace.client.repository.database.h2.impl;

import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.entities.setting.*;
import ru.kidesoft.ticketplace.client.domain.models.entities.setting.enums.PageOrientation;
import ru.kidesoft.ticketplace.client.domain.models.entities.setting.enums.PageSize;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.repository.database.h2.ConstantNames;
import ru.kidesoft.ticketplace.client.repository.database.h2.TableNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.UUID;

public class SettingRepository implements DatabaseDao.SettingDao {
    private Connection connection;

    public SettingRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UUID save(Setting setting) throws DbException {
        String sql = String.format("SELECT ID FROM FINAL TABLE(MERGE INTO %s (" +
                "KKT_AUTO_RECONNECT, " +
                "KKT_DRIVER_PATH, " +
                "PRINTER_NAME, " +
                "PAGE_SIZE, " +
                "PAGE_ORIENTATION, " +
                "PRINT_CHECK, " +
                "PRINT_TICKET, " +
                "UPDATE_REPOSITORY_URL, " +
                "UPDATE_AUTOMATICALLY, " +
                "SERVER_REQUEST_TIMEOUT, " +
                "SERVER_REQUEST_INTERVAL, " +
                "LOGIN_ID" +
                ") KEY(LOGIN_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SELECT VAL FROM %s WHERE NAME = ?))", TableNames.SETTING.getName(), TableNames.CONSTANT.getName());

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, setting.getKkt().getKktAutoRecconnect());
            preparedStatement.setObject(2, setting.getKkt().getKktDriverPath());
            preparedStatement.setObject(3, setting.getPrinter().getName());
            preparedStatement.setObject(4, setting.getPrinter().getPageSize().getId());
            preparedStatement.setObject(5, setting.getPrinter().getPageOrientation().getId());
            preparedStatement.setObject(6, setting.getPrinter().getPrintCheck());
            preparedStatement.setObject(7, setting.getPrinter().getPrintTicket());
            preparedStatement.setObject(8, setting.getUpdate().getRepository());
            preparedStatement.setBoolean(9, setting.getUpdate().getAuto());
            preparedStatement.setObject(10, setting.getServer().getTimeout().toNanos());
            preparedStatement.setObject(11, setting.getServer().getInterval().toNanos());
            preparedStatement.setString(12, ConstantNames.ACTIVE_LOGIN_UUID.getName());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getObject(1, UUID.class);
            } else {
                throw new SQLException("No ID returned from database");
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public UUID setDefault() throws DbException {
        String sql = String.format("SELECT ID FROM FINAL TABLE(MERGE INTO %s(LOGIN_ID) KEY(LOGIN_ID) VALUES (SELECT VAL FROM %s WHERE NAME = ?))", TableNames.SETTING.getName(), TableNames.CONSTANT.getName());
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

    @Override
    public Setting getSetting() throws DbException {
        String sql = MessageFormat.format("SELECT * FROM {1} LEFT JOIN {0} ON {0}.LOGIN_ID = {1}.VAL WHERE {1}.NAME = ?", TableNames.SETTING.getName(), TableNames.CONSTANT.getName());

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, ConstantNames.ACTIVE_LOGIN_UUID.getName());
            ResultSet resultSet = preparedStatement.executeQuery();


            if (!resultSet.next()) {
                throw new DbException(new NullPointerException("setting return no uuid"));
            }

            var kktAutoReconnect = resultSet.getBoolean("KKT_AUTO_RECONNECT");
            var kktDriverPath = resultSet.getString("KKT_DRIVER_PATH");
            var printerName = resultSet.getString("PRINTER_NAME");
            var pageSize = PageSize.getById(resultSet.getInt("PAGE_SIZE"));
            var pageOrientation = PageOrientation.getById(resultSet.getInt("PAGE_ORIENTATION"));
            var printCheck = resultSet.getBoolean("PRINT_CHECK");
            var printTicket = resultSet.getBoolean("PRINT_TICKET");
            var updateRepository = resultSet.getString("UPDATE_REPOSITORY_URL");
            var updateAuto = resultSet.getBoolean("UPDATE_AUTOMATICALLY");
            var timeout = Duration.ofNanos(resultSet.getLong("SERVER_REQUEST_TIMEOUT"));
            var interval =  Duration.ofNanos(resultSet.getLong("SERVER_REQUEST_INTERVAL"));

            var kkt = KktBuilder.aKkt().withKktAutoRecconnect(kktAutoReconnect)
                    .withKktDriverPath(kktDriverPath).build();
            var printer = PrinterBuilder.aPrinter().withName(printerName)
                    .withPageOrientation(pageOrientation).withPageSize(pageSize)
                    .withPrintTicket(printTicket).withPrintCheck(printCheck).build();

            var server = ServerBuilder.aServer().withInterval(interval).withTimeout(timeout).build();
            var update = UpdateBuilder.anUpdate().withAuto(updateAuto).withRepository(updateRepository).build();

            return SettingBuilder.aSetting().withKkt(kkt).withPrinter(printer).withServer(server).withUpdate(update).build();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

}
