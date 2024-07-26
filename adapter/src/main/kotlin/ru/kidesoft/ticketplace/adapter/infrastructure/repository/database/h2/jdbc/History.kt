package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.jdbc

import ru.kidesoft.ticketplace.adapter.application.port.HistoryPort
import ru.kidesoft.ticketplace.adapter.application.port.SessionPort
import ru.kidesoft.ticketplace.adapter.domain.history.ErrorStatus
import ru.kidesoft.ticketplace.adapter.domain.history.History
import ru.kidesoft.ticketplace.adapter.domain.history.HistoryInfo
import ru.kidesoft.ticketplace.adapter.domain.history.Step
import ru.kidesoft.ticketplace.adapter.domain.order.OperationType
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.sql.Connection
import java.sql.ResultSet
import java.time.ZonedDateTime
import java.util.UUID

class HistoryRepositoryJdbc(val connection: Connection, val sessionPort: SessionPort) : HistoryPort {
    fun ResultSet.toHistory(): History = History(
        id = this.getObject("ID", UUID::class.java),
        loginId = this.getObject("LOGIN_ID", UUID::class.java),
        orderId = this.getInt("ORDER_ID"),
        stepType = Step.entries.find { it.ordinal == this.getInt("STEP_TYPE") } ?: Step.UNDEFINED,
        HistoryInfo(
            sourceType = SourceType.entries.find { it.ordinal == this.getInt("SOURCE_TYPE") } ?: SourceType.UNDEFINED,
            createdAt = this.getObject("CREATED_AT", ZonedDateTime::class.java),
            error = this.getString("ERROR"),
            errorStatus = ErrorStatus.entries.find { it.ordinal == this.getInt("ERROR_STATUS") }
                ?: ErrorStatus.UNDEFINED,
            operationType = OperationType.entries.find { it.ordinal == this.getInt("OPERATION_TYPE") }
                ?: OperationType.UNDEFINED
        )
    )

    override fun saveByCurrent(orderId: Int, step: Step, historyInfo: HistoryInfo): UUID? {
        val loginId = sessionPort.getActive()?.loginId ?: throw IllegalArgumentException("There's no active session in database")

        val sql =
            "SELECT ID FROM FINAL TABLE(MERGE INTO $HISTORY_TABLE(LOGIN_ID, CREATED_AT, ERROR_STATUS, ERROR, ORDER_ID, OPERATION_TYPE, SOURCE_TYPE, STEP_TYPE)  KEY(LOGIN_ID, ORDER_ID, STEP_TYPE) VALUES (?, ?, ?, ?, ?, ?, ?, ?))"

        connection.prepareStatement(sql).use { statement ->
            statement.setObject(1, loginId)
            statement.setObject(2, historyInfo.createdAt)
            statement.setInt(3, historyInfo.errorStatus.ordinal)
            statement.setString(4, historyInfo.error)
            statement.setInt(5, orderId)
            statement.setInt(6, historyInfo.operationType.ordinal)
            statement.setInt(7, historyInfo.sourceType.ordinal)
            statement.setInt(8, step.ordinal)
            statement.executeQuery().use {
                return if (it.next()) it.getObject("ID", UUID::class.java) else null
            }
        }

    }

    override fun getListByCurrent(from: ZonedDateTime?): List<History> {
        val loginId = sessionPort.getActive() ?.loginId ?: throw IllegalArgumentException("There's no active session in database")
        val sql = "SELECT * FROM $HISTORY_TABLE WHERE LOGIN_ID = ?"

        connection.prepareStatement(sql).use { statement ->
            statement.setObject(1, loginId)

            statement.executeQuery().use { resultSet ->
                val list  = mutableListOf<History>()

                while (resultSet.next()) {
                    list.add(resultSet.toHistory())
                }

                return list
            }
        }
    }
}