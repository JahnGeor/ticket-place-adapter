package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.kidesoft.ticketplace.adapter.application.port.ClickPort
import ru.kidesoft.ticketplace.adapter.domain.click.Click
import ru.kidesoft.ticketplace.adapter.domain.click.ClickDatabase
import ru.kidesoft.ticketplace.adapter.domain.click.ClickExposed
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace.ClickData
import java.util.UUID
import kotlin.properties.Delegates

class ClickRepository(val database: Database) : ClickPort {
    object ClickTable : UUIDTable("CLICK", "ID") {
        val loginId = uuid("LOGIN_ID").references(LoginRepository.LoginTable.id).uniqueIndex()
        val clickId = integer("CLICK_ID")
    }

    override fun getByCurrent(): ClickDatabase? {
        return transaction {
            ClickTable.join(
                SessionRepository.SessionTable,
                JoinType.INNER,
                additionalConstraint = { (SessionRepository.SessionTable.loginId eq ClickTable.loginId) and (SessionRepository.SessionTable.active eq true) }
            ).selectAll().map { ClickDatabase(it[ClickTable.id].value, it[ClickTable.loginId], it[ClickTable.clickId]) }
                .singleOrNull()
        }
    }

    override fun save(clickExposed: ClickExposed): UUID {
        return transaction {
            val loginId =
                SessionRepository.SessionTable.selectAll().where { SessionRepository.SessionTable.active eq true }.map {
                    it[SessionRepository.SessionTable.loginId]
                }.single()

            return@transaction getByLoginId(loginId)?.let {
                update(it.id, clickExposed)
                it.id
            } ?: create(loginId, clickExposed)
        }
    }

    private fun getByLoginId(loginId: UUID): ClickDatabase? {
        return transaction {
            ClickTable.selectAll().where { ClickTable.loginId eq loginId }.map {
                ClickDatabase(it[ClickTable.id].value, it[ClickTable.loginId], it[ClickTable.clickId])
            }.singleOrNull()
        }
    }

    fun update(id: UUID, clickExposed: ClickExposed): Int = ClickTable.update({ ClickTable.id eq id }) {
        it[clickId] = clickExposed.clickId
    }

    fun create(loginId: UUID, clickExposed: ClickExposed): UUID = (ClickTable.insert {
        it[clickId] = clickExposed.clickId
        it[ClickTable.loginId] = loginId
    } get ClickTable.id).value
}

