package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.jdbc

import ru.kidesoft.ticketplace.adapter.application.port.ClickPort
import ru.kidesoft.ticketplace.adapter.application.port.SessionPort
import ru.kidesoft.ticketplace.adapter.domain.click.Click
import ru.kidesoft.ticketplace.adapter.domain.click.ClickInfo
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.sql.Connection
import java.sql.SQLException
import java.time.ZonedDateTime
import java.util.*

class ClickRepositoryJdbc(private val connection: Connection, private val sessionPort : SessionPort) : ClickPort {

    override fun saveByCurrent(clickInfo: ClickInfo): UUID {

        val loginId = sessionPort.getActive()?.loginId ?: throw SQLException("LOGIN_ID can't be null")

        val sql = "SELECT ID FROM FINAL TABLE(MERGE INTO $CLICK_TABLE(LOGIN_ID, CLICK_ID, ORDER_ID, SOURCE_TYPE, CREATED_AT) KEY(LOGIN_ID) VALUES(?,?,?,?, ?))"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.setObject(1, loginId)
            preparedStatement.setInt(2, clickInfo.clickId)
            preparedStatement.setInt(3, clickInfo.orderId)
            preparedStatement.setInt(4, clickInfo.sourceType.ordinal)
            preparedStatement.setObject(5, clickInfo.createdAt)
            preparedStatement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    return resultSet.getObject("ID", UUID::class.java)
                } else throw SQLException("No click ID returned from database")
            }
        }
    }

    override fun getByCurrent(): Click? {
        val loginId = sessionPort.getActive()?.loginId ?: throw SQLException("LOGIN_ID can't be null")

        val sql = "SELECT * FROM CLICK WHERE LOGIN_ID = ?"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.setObject(1, loginId)
            preparedStatement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    return Click(
                        resultSet.getObject("ID", UUID::class.java),
                        resultSet.getInt("ORDER_ID"),
                        resultSet.getInt("CLICK_ID"),
                        SourceType.entries.find { it.ordinal == resultSet.getInt("SOURCE_TYPE")} ?: SourceType.UNDEFINED,
                        resultSet.getObject("CREATED_AT", ZonedDateTime::class.java)
                    )
                } else return null
            }
        }
    }
}