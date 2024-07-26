package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.jdbc

import org.flywaydb.core.internal.database.h2.H2DatabaseType
import org.h2.api.H2Type
import ru.kidesoft.ticketplace.adapter.application.port.LoginPort
import ru.kidesoft.ticketplace.adapter.domain.exception.DbException
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import ru.kidesoft.ticketplace.adapter.domain.login.LoginInfo
import java.sql.Array
import java.sql.Connection
import java.util.*
import java.util.stream.Stream

class LoginRepositoryJdbc(private val connection: Connection) : LoginPort {

    override fun getLoginId(email: String, url: String): UUID? {
        val preparedStatement = connection.prepareStatement("SELECT id FROM LOGIN WHERE email = ? AND url = ?")
        preparedStatement.setString(1, email)
        preparedStatement.setString(2, url)
        val resultSet = preparedStatement.executeQuery()
        val id = if (resultSet.next()) UUID.fromString(resultSet.getString("id")) else null
        preparedStatement.close()
        return id
    }

    override fun save(login: LoginInfo): UUID {
        val sql = "SELECT ID FROM FINAL TABLE(MERGE INTO $LOGIN_TABLE(EMAIL, PASSWORD, URL) KEY(EMAIL, URL) VALUES (?, ?, ?))"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.setString(1, login.email)
            preparedStatement.setString(2, login.password)
            preparedStatement.setString(3, login.url)
            preparedStatement.executeQuery().use { resultSet ->
                val id = if (resultSet.next()) UUID.fromString(resultSet.getString("id")) else throw DbException("Не удалось получить идентификатор сохраненных данных по авторизации пользоваателя")
                return id
            }
        }
    }

    override fun getAll(): List<Login> {
        val sql = "SELECT * FROM $LOGIN_TABLE"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.executeQuery().use { resultSet ->
                val list = mutableListOf<Login>()
                while (resultSet.next()) {
                    list.add(Login(
                        resultSet.getObject("id", UUID::class.java),
                        resultSet.getString("email"),
                        resultSet.getString("url"),
                        resultSet.getString("password")
                    ))
                }

                return list
            }
        }
    }

    override fun getByCurrent(): Login? {
        val sql = "SELECT l.id, l.url, l.email, l.password FROM LOGIN as l LEFT JOIN SESSION as s ON s.LOGIN_ID = l.id WHERE s.ACTIVE = true LIMIT 1"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    return Login(
                        resultSet.getObject("id", UUID::class.java),
                        resultSet.getString("email"),
                        resultSet.getString("url"),
                        resultSet.getString("password")
                    )
                } else {
                    return null
                }
            }
        }
    }

    override fun deleteAll() : Int {
        val sql = "DELETE FROM $LOGIN_TABLE"
        connection.prepareStatement(sql).use { preparedStatement ->
            return preparedStatement.executeUpdate()
        }
    }

    override fun deleteById(vararg id : UUID) : Int {
        val sql = "DELETE FROM $LOGIN_TABLE WHERE ID IN ?"

        val arrayOfId = connection.createArrayOf("UUID", id)

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.setArray(1, arrayOfId)
            return preparedStatement.executeUpdate()
        }
    }
}