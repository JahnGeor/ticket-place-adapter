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
    object Sessions : UUIDTable("SESSION") {
        val loginId = uuid("LOGIN_ID").references(LoginRepository.Logins.id)
        val createdAt = timestampWithTimeZone("CREATED_AT")
        val expiredAt = timestampWithTimeZone("EXPIRED_AT")
        val tokenType = varchar("TOKEN_TYPE", 30)
        val token = varchar("ACCESS_TOKEN", 100)
        val active = bool("ACTIVE")
    }

    private fun ResultRow.toSession() = Session().apply {
        this.token.value = this@toSession[Sessions.token]
        this.token.type = this@toSession[Sessions.tokenType]
        this.token.createdTime = this@toSession[Sessions.createdAt].toZonedDateTime()
        this.token.expiredTime = this@toSession[Sessions.expiredAt].toZonedDateTime()
        this.loginId = this@toSession[Sessions.loginId]
        this.id = this@toSession[Sessions.id].value
        this.active = this@toSession[Sessions.active]
    }

    override fun getActive(): Session? {
        return transaction {
            Sessions.selectAll().where { Sessions.active eq true }.map {
                it.toSession()
            }.singleOrNull()
        }
    }

    override fun create(sessionExposed: SessionExposed): Session {
        return transaction {
            val id = Sessions.insert {
                it[Sessions.loginId] = sessionExposed.loginId
                it[Sessions.token] = sessionExposed.token.value
                it[Sessions.createdAt] = sessionExposed.token.createdTime.toOffsetDateTime()
                it[Sessions.expiredAt] = sessionExposed.token.expiredTime.toOffsetDateTime()
                it[Sessions.active] = sessionExposed.active
                it[Sessions.tokenType] = sessionExposed.token.type
            } get Sessions.id

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
            Sessions.update({ Sessions.id eq sessionId }) {
                it[Sessions.loginId] = sessionExposed.loginId
                it[Sessions.token] = sessionExposed.token.value
                it[Sessions.createdAt] = sessionExposed.token.createdTime.toOffsetDateTime()
                it[Sessions.expiredAt] = sessionExposed.token.expiredTime.toOffsetDateTime()
                it[Sessions.active] = sessionExposed.active
                it[Sessions.tokenType] = sessionExposed.token.type
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
            Sessions.selectAll().where { Sessions.loginId eq loginId }.map {
                it.toSession()
            }.singleOrNull()
        }
    }

    override fun setActive(sessionId: UUID) {
        transaction {
            setDeactive()

            Sessions.update({ Sessions.id eq sessionId }) {
                it[Sessions.active] = true
            }

        }
    }

    override fun setDeactive() {
        transaction {
            Sessions.update(where = { Sessions.active eq true }) {
                it[Sessions.active] = false
            }
        }
    }

    override fun deleteActive() {
        transaction {
            Sessions.deleteWhere {active eq true}
        }
    }

    override fun deleteAll() {
        Sessions.deleteAll()
    }

    override fun deleteById(id: UUID) {
        Sessions.deleteWhere {
            Sessions.id eq id
        }
    }


}