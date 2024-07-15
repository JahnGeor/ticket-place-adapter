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
    object Logins : UUIDTable("LOGIN") {
        val email: Column<String> = varchar("email", 256)
        val password: Column<String> = varchar("password", 256)
        val url: Column<String> = varchar("url", 256)

        val uniqueEmailUrl = uniqueIndex(email, url)
    }

    override fun Create(login: LoginExposed): Login {
        return transaction(database) {
            val id = Logins.insert {
                it[email] = login.email
                it[password] = login.password
                it[url] = login.url
            } get Logins.id

            Login().apply {
                this.email = login.email
                this.password = login.password
                this.url = login.url
                this.id = id.value
            }
        }
    }

    override fun Update(id: UUID, login: LoginExposed): Login {
        return transaction {
            Logins.update({Logins.id eq id}) {
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

    override fun GetLoginId(email: String, url: String): UUID? {
        return transaction {
            Logins.select(Logins.id).where(Logins.email.eq(email) and Logins.url.eq(url)).singleOrNull()?.let {
                it[Logins.id].value
            }
        }
    }

    override fun GetAll(): List<Login> {
        return transaction {
            Logins.selectAll().map{
                Login().apply {
                    id = it[Logins.id].value
                    email = it[Logins.email]
                    password = it[Logins.password]
                    url = it[Logins.url]
                }
            }
        }
    }

}