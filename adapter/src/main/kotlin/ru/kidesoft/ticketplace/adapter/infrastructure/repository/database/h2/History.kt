package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.javatime.timestampWithTimeZone
import org.jetbrains.exposed.sql.transactions.transaction
import ru.kidesoft.ticketplace.adapter.application.port.HistoryPort
import ru.kidesoft.ticketplace.adapter.domain.history.ErrorStatus
import ru.kidesoft.ticketplace.adapter.domain.history.History
import ru.kidesoft.ticketplace.adapter.domain.history.HistoryPayload
import ru.kidesoft.ticketplace.adapter.domain.history.Step
import ru.kidesoft.ticketplace.adapter.domain.order.OperationType
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import ru.kidesoft.ticketplace.adapter.domain.order.StatusType
import java.time.ZonedDateTime
import java.util.*

class HistoryRepository(val database: Database) : HistoryPort {

    object HistoryTable : UUIDTable("HISTORY", "ID") {

        // LOGIN_ID
        val loginId = uuid("LOGIN_ID").references(LoginRepository.LoginTable.id)

        // CREATED_AT
        val createdAt = timestampWithTimeZone("CREATED_AT").clientDefault { ZonedDateTime.now().toOffsetDateTime() }

        // STATUS
        val status = enumeration<ErrorStatus>("STATUS")

        // ERROR
        val error = varchar("ERROR", 500)

        // ORDER_ID
        val orderId = integer("ORDER_ID")

        // OPERATION_TYPE
        val operationType = enumeration<OperationType>("OPERATION_TYPE")

        // SOURCE_TYPE
        val sourceType = enumeration<SourceType>("SOURCE_TYPE")

        val stepType = enumeration<Step>("STEP_TYPE").default(Step.UNDEFINED)

        val uniqueIndex = uniqueIndex("UNIQUE_INDEX", loginId, orderId)
    }

    private fun ResultRow.toEntity() = History(
        this@toEntity[HistoryTable.id].value,
        this@toEntity[HistoryTable.loginId],
        this@toEntity[HistoryTable.orderId],
        this@toEntity[HistoryTable.stepType],
        HistoryPayload(
            this@toEntity[HistoryTable.sourceType],
            this@toEntity[HistoryTable.createdAt].toZonedDateTime(),
            this@toEntity[HistoryTable.error],
            this@toEntity[HistoryTable.status],
            this@toEntity[HistoryTable.operationType]
        )
    )

    /** Данный метод запрашивает список записей в таблице истории по текущему пользователю
     *
     */
    override fun getListByCurrent(from : ZonedDateTime?): List<History> {
        return transaction {
            var historyQuery = HistoryTable.join(
                SessionRepository.SessionTable,
                JoinType.INNER,
                additionalConstraint = { (SessionRepository.SessionTable.loginId eq HistoryTable.loginId) and (SessionRepository.SessionTable.active eq true) }
            ).selectAll()

            if (from != null) {
                historyQuery = historyQuery.where{HistoryTable.createdAt greaterEq from.toOffsetDateTime()}
            }

            return@transaction historyQuery.map { it.toEntity() }
        }

    }

    override fun save(orderId: Int, step: Step, historyPayload: HistoryPayload): History {

        return transaction(database) {
            val loginId =
                SessionRepository.SessionTable.selectAll().where { SessionRepository.SessionTable.active eq true }.map {
                    it[SessionRepository.SessionTable.loginId]
                }.single()

            var historyId = getByOrderIdAndLoginId(loginId, orderId, step)

            if (historyId == null) {
                historyId = create(loginId, orderId, step, historyPayload)
            } else {
                update(historyId, historyPayload)
            }

            return@transaction History(historyId, loginId, orderId, step, historyPayload)
        }
    }

    private fun getByOrderIdAndLoginId(loginId: UUID, orderId: Int, step: Step): UUID? {
        return transaction(database) {
            HistoryTable.selectAll().where { (HistoryTable.loginId eq loginId) and (HistoryTable.orderId eq orderId) and (HistoryTable.stepType eq step) }
                .map {
                    it[HistoryTable.id].value
                }.singleOrNull()
        }
    }

    fun create(loginId: UUID, orderId: Int, step: Step, historyExposed: HistoryPayload): UUID {
        return transaction(database) {
            (HistoryTable.insert {
                it[HistoryTable.loginId] = loginId
                it[HistoryTable.orderId] = orderId
                it[operationType] = historyExposed.operationType
                it[error] = historyExposed.error
                it[status] = historyExposed.statusType
                it[sourceType] = historyExposed.sourceType
                it[createdAt] = historyExposed.createdAt.toOffsetDateTime()
                it[stepType] = step
            } get HistoryTable.id).value
        }
    }

    fun update(historyId: UUID, historyExposed: HistoryPayload) {
        transaction(database) {
            HistoryTable.update({ HistoryTable.id eq historyId }) {
                it[status] = historyExposed.statusType
                it[error] = historyExposed.error
                it[operationType] = historyExposed.operationType
                it[sourceType] = historyExposed.sourceType
                it[createdAt] = historyExposed.createdAt.toOffsetDateTime()
            }
        }
    }
}