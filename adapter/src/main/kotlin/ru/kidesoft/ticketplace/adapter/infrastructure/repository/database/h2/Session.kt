package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.timestampWithTimeZone
import org.jetbrains.exposed.sql.transactions.transaction
import ru.kidesoft.ticketplace.adapter.application.port.SessionPort
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.domain.session.SessionExposed
import java.util.*

class SessionRepository(private val database: Database) : SessionPort {
    object SessionTable : UUIDTable("SESSION") {
        val loginId = uuid("LOGIN_ID").references(LoginRepository.LoginTable.id)
        val createdAt = timestampWithTimeZone("CREATED_AT")
        val expiredAt = timestampWithTimeZone("EXPIRED_AT")
        val tokenType = varchar("TOKEN_TYPE", 30)
        val token = varchar("ACCESS_TOKEN", 100)
        val active = bool("ACTIVE")
    }

    private fun ResultRow.toSession() = Session().apply {
        this.token.value = this@toSession[SessionTable.token]
        this.token.type = this@toSession[SessionTable.tokenType]
        this.token.createdTime = this@toSession[SessionTable.createdAt].toZonedDateTime()
        this.token.expiredTime = this@toSession[SessionTable.expiredAt].toZonedDateTime()
        this.loginId = this@toSession[SessionTable.loginId]
        this.id = this@toSession[SessionTable.id].value
        this.active = this@toSession[SessionTable.active]
    }

    override fun getActive(): Session? {
        return transaction {
            SessionTable.selectAll().where { SessionTable.active eq true }.map {
                it.toSession()
            }.singleOrNull()
        }
    }

    override fun create(sessionExposed: SessionExposed): Session {
        return transaction {
            val id = SessionTable.insert {
                it[SessionTable.loginId] = sessionExposed.loginId
                it[SessionTable.token] = sessionExposed.token.value
                it[SessionTable.createdAt] = sessionExposed.token.createdTime.toOffsetDateTime()
                it[SessionTable.expiredAt] = sessionExposed.token.expiredTime.toOffsetDateTime()
                it[SessionTable.active] = sessionExposed.active
                it[SessionTable.tokenType] = sessionExposed.token.type
            } get SessionTable.id

            Session().apply {
                this.id = id.value
                this.token = sessionExposed.token
                this.active = sessionExposed.active
                this.loginId = sessionExposed.loginId
            }
        }
    }

    override fun update(sessionId: UUID, sessionExposed: SessionExposed): Session {
        return transaction {
            SessionTable.update({ SessionTable.id eq sessionId }) {
                it[SessionTable.loginId] = sessionExposed.loginId
                it[SessionTable.token] = sessionExposed.token.value
                it[SessionTable.createdAt] = sessionExposed.token.createdTime.toOffsetDateTime()
                it[SessionTable.expiredAt] = sessionExposed.token.expiredTime.toOffsetDateTime()
                it[SessionTable.active] = sessionExposed.active
                it[SessionTable.tokenType] = sessionExposed.token.type
            }

            Session().apply {
                this.id = sessionId
                this.loginId = sessionExposed.loginId
                this.active = sessionExposed.active
                this.token = sessionExposed.token
            }
        }
    }

    override fun getByLoginId(loginId: UUID): Session? {
        return transaction {
            SessionTable.selectAll().where { SessionTable.loginId eq loginId }.map {
                it.toSession()
            }.singleOrNull()
        }
    }

    override fun setActive(sessionId: UUID) {
        transaction {
            setDeactive()

            SessionTable.update({ SessionTable.id eq sessionId }) {
                it[SessionTable.active] = true
            }

        }
    }

    override fun setDeactive() {
        transaction {
            SessionTable.update(where = { SessionTable.active eq true }) {
                it[SessionTable.active] = false
            }
        }
    }

    override fun deleteActive() {
        transaction {
            SessionTable.deleteWhere {active eq true}
        }
    }

    override fun deleteAll() {
        return transaction { SessionTable.deleteAll() }
    }

    override fun deleteById(id: UUID) {
        return transaction {
            connection.autoCommit = false
            SessionTable.deleteWhere {
                SessionTable.id eq id
            }
        }
    }


}