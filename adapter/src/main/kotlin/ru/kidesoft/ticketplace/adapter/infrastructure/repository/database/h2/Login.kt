package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.kidesoft.ticketplace.adapter.application.port.LoginPort
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed
import java.util.UUID


class LoginRepository(private val database: Database) : LoginPort {
    object LoginTable : UUIDTable("LOGIN") {
        val email: Column<String> = varchar("email", 256)
        val password: Column<String> = varchar("password", 256)
        val url: Column<String> = varchar("url", 256)

        val uniqueEmailUrl = uniqueIndex(email, url)
    }

    override fun create(login: LoginExposed): Login {
        return transaction(database) {
            val id = LoginTable.insert {
                it[email] = login.email
                it[password] = login.password
                it[url] = login.url
            } get LoginTable.id

            Login().apply {
                this.email = login.email
                this.password = login.password
                this.url = login.url
                this.id = id.value
            }
        }
    }

    override fun update(id: UUID, login: LoginExposed): Login {
        return transaction {
            LoginTable.update({LoginTable.id eq id}) {
                it[email] = login.email
                it[password] = login.password
                it[url] = login.url
            }

            Login().apply {
                this.email = login.email
                this.password = login.password
                this.url = login.url
                this.id = id
            }
        }
    }

    override fun getLoginId(email: String, url: String): UUID? {
        return transaction {
            LoginTable.select(LoginTable.id).where(LoginTable.email.eq(email) and LoginTable.url.eq(url)).singleOrNull()?.let {
                it[LoginTable.id].value
            }
        }
    }

    override fun getAll(): List<Login> {
        return transaction {
            LoginTable.selectAll().map{
                Login().apply {
                    id = it[LoginTable.id].value
                    email = it[LoginTable.email]
                    password = it[LoginTable.password]
                    url = it[LoginTable.url]
                }
            }
        }
    }

    override fun getByCurrent(): Login? {
        return transaction {
            LoginTable.join(SessionRepository.SessionTable, JoinType.INNER, additionalConstraint = {(SessionRepository.SessionTable.loginId eq LoginTable.id) and (SessionRepository.SessionTable.active eq true)}).selectAll().map {
                Login().apply {
                    id = it[LoginTable.id].value
                    email = it[LoginTable.email]
                    password = it[LoginTable.password]
                    url = it[LoginTable.url]
                }
            }.singleOrNull()
        }

    }

}