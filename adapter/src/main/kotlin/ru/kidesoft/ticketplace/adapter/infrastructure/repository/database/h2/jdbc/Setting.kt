package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.jdbc

import ru.kidesoft.ticketplace.adapter.application.port.SessionPort
import ru.kidesoft.ticketplace.adapter.application.port.SettingPort
import ru.kidesoft.ticketplace.adapter.domain.setting.*
import java.sql.Connection
import java.sql.ResultSet
import java.time.Duration
import java.util.*

class SettingRepositoryJdbc(private val connection: Connection, private val sessionPort: SessionPort) : SettingPort {
    private fun ResultSet.toSetting() : Setting =

            Setting(
                this.getObject("ID", UUID::class.java),
                this.getObject("LOGIN_ID", UUID::class.java),
                KktSetting(this.getString("KKT_DRIVER_PATH"), this.getBoolean("KKT_AUTO_RECONNECT"), this.getBoolean("KKT_PRINT_TIME_CHECK")),
                ServerSetting(Duration.ofNanos(this.getLong("SERVER_REQUEST_INTERVAL")), Duration.ofNanos(this.getLong("SERVER_REQUEST_TIMEOUT"))),
                UpdateSetting(this.getBoolean("UPDATE_AUTOMATICALLY"), this.getString("UPDATE_REPOSITORY_URL")),
                PrintSetting(
                    this.getBoolean("PRINT_CHECK"),
                    this.getBoolean("PRINT_TICKET"),
                    PageSize.entries.find { it.ordinal == this.getInt("PAGE_SIZE") } ?: PageSize.UNDEFINED,
                    PageOrientation.entries.find { it.ordinal == this.getInt("PAGE_ORIENTATION")} ?: PageOrientation.UNDEFINED,
                    this.getString("PRINTER_NAME")
                ),
            )

    override fun saveByCurrent(setting: SettingInfo): UUID {
        val loginId = sessionPort.getActive()?.loginId ?: throw IllegalArgumentException()
        return saveByLoginId(loginId, setting)
    }

    override fun saveByLoginId(loginId: UUID, setting: SettingInfo) : UUID {
        val sql = "SELECT ID FROM FINAL TABLE(MERGE INTO $SETTING_TABLE" +
                "(KKT_AUTO_RECONNECT, KKT_DRIVER_PATH, KKT_PRINT_TIME_CHECK, PRINTER_NAME, " +
                "PAGE_SIZE, PAGE_ORIENTATION, PRINT_CHECK, PRINT_TICKET, " +
                "UPDATE_REPOSITORY_URL, UPDATE_AUTOMATICALLY, " +
                "SERVER_REQUEST_TIMEOUT, SERVER_REQUEST_INTERVAL, LOGIN_ID)  " +
                "KEY(LOGIN_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?))"

        connection.prepareStatement(sql).use { statement ->
            statement.setBoolean(1, setting.kkt.autoReconnect)
            statement.setString(2, setting.kkt.path)
            statement.setBoolean(3, setting.kkt.printTimeCheck)
            statement.setString(4, setting.print.printerName)
            statement.setInt(5, setting.print.pageSize.ordinal)
            statement.setInt(6, setting.print.pageOrientation.ordinal)
            statement.setBoolean(7, setting.print.isPrintingCheck)
            statement.setBoolean(8, setting.print.isPrintingTicket)
            statement.setString(9, setting.update.updateUrl)
            statement.setBoolean(10, setting.update.isAuto)
            statement.setLong(11, setting.server.requestTimeout.toNanos())
            statement.setLong(12, setting.server.requestInterval.toNanos())
            statement.setObject(13, loginId)

            statement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    return resultSet.getObject("ID", UUID::class.java)
                } else throw IllegalArgumentException("Save method of Setting Repository return no id")
            }
        }
    }

    override fun getByCurrent(): Setting? {
        val loginId = sessionPort.getActive()?.loginId ?: throw IllegalArgumentException("No active session is found")
        return getByLoginId(loginId)
    }

    override fun getById(id: UUID): Setting? {
        val sql = "SELECT * FROM $SETTING_TABLE WHERE ID = ?"

        connection.prepareStatement(sql).use {
                preparedStatement ->
            preparedStatement.setObject(1, id)
            preparedStatement.executeQuery().use {
                    resultSet -> return resultSet.toSetting()
            }
        }
    }

    override fun getByLoginId(loginId: UUID): Setting? {
        val sql = "SELECT * FROM $SETTING_TABLE WHERE LOGIN_ID=?"

        connection.prepareStatement(sql).use {
                preparedStatement ->
            preparedStatement.setObject(1, loginId)
            preparedStatement.executeQuery().use {
                    resultSet -> return if (resultSet.next()) resultSet.toSetting() else null
            }
        }
    }

    override fun setDefault(loginId: UUID): UUID {
        val defaultSetting = SettingInfo.getDefault()
        return saveByLoginId(loginId, defaultSetting)
    }
}