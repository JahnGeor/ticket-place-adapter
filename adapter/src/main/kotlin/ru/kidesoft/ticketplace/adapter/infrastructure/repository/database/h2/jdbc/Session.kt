package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.jdbc

import net.datafaker.providers.base.Bool
import ru.kidesoft.ticketplace.adapter.application.port.SessionPort
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.domain.session.SessionInfo
import ru.kidesoft.ticketplace.adapter.domain.session.Token
import java.sql.Connection
import java.sql.SQLException
import java.time.ZonedDateTime
import java.util.*
import kotlin.math.log

class SessionRepositoryJdbc(val connection: Connection) : SessionPort {

    override fun saveByCurrent(sessionInfo: SessionInfo, active: Boolean) : UUID {
        val loginId = getActive()?.loginId ?: throw IllegalArgumentException("There is no active session in database")
        return saveByLoginId(loginId, sessionInfo, active)
    }

    override fun saveByLoginId(loginId: UUID, sessionInfo: SessionInfo, active: Boolean): UUID {
        if (active) {
            deactivateExcept(loginId)
        }

        val sqlMerge =
            "SELECT ID FROM FINAL TABLE(MERGE INTO $SESSION_TABLE(LOGIN_ID, ACTIVE, CREATED_AT, EXPIRED_AT, TOKEN_TYPE, ACCESS_TOKEN) KEY (LOGIN_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?));"
        connection.prepareStatement(sqlMerge).use { preparedStatement ->
            preparedStatement.setObject(1, loginId)
            preparedStatement.setBoolean(2, active)
            preparedStatement.setObject(3, sessionInfo.token.createdTime)
            preparedStatement.setObject(4, sessionInfo.token.expiredTime)
            preparedStatement.setString(5, sessionInfo.token.type)
            preparedStatement.setString(6, sessionInfo.token.value)

            preparedStatement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    return UUID.fromString(resultSet.getString("ID"))
                } else throw SQLException("В процессе выполнения метода SessionRepostirory.saveByLoginId() запрос не вернул id измененных/созданных строк")
            }
        }
    }

    override fun deactivateExcept(loginId: UUID): Int {
        val sql = "UPDATE $SESSION_TABLE SET ACTIVE = FALSE WHERE LOGIN_ID <> ?;"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.setObject(1, loginId)
            return preparedStatement.executeUpdate()
        }
    }

    override fun getActive(): Session? {
        val sql = "SELECT * FROM $SESSION_TABLE WHERE ACTIVE = TRUE ORDER BY CREATED_AT DESC LIMIT 1"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    return Session(
                        id = resultSet.getObject("ID", UUID::class.java),
                        loginId = resultSet.getObject("LOGIN_ID", UUID::class.java),
                        token = Token(
                            type = resultSet.getString("TOKEN_TYPE"),
                            value = resultSet.getString("ACCESS_TOKEN"),
                            createdTime = resultSet.getObject("CREATED_AT", ZonedDateTime::class.java),
                            expiredTime = resultSet.getObject("EXPIRED_AT", ZonedDateTime::class.java)
                        ),
                        active = resultSet.getBoolean("ACTIVE"),
                    )
                } else return null
            }
        }
    }


    override fun getByLoginId(loginId: UUID): Session? {
        val sql = "SELECT * FROM $SESSION_TABLE WHERE LOGIN_ID = ? LIMIT 1;"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.setObject(1, loginId)
            preparedStatement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    return Session(
                        id = resultSet.getObject("ID", UUID::class.java),
                        loginId = resultSet.getObject("LOGIN_ID", UUID::class.java),
                        token = Token(
                            type = resultSet.getString("TOKEN_TYPE"),
                            value = resultSet.getString("ACCESS_TOKEN"),
                            createdTime = resultSet.getObject("CREATED_AT", ZonedDateTime::class.java),
                            expiredTime = resultSet.getObject("EXPIRED_AT", ZonedDateTime::class.java)
                        ),
                        active = resultSet.getBoolean("ACTIVE"),
                    )
                } else return null
            }
        }
    }

    override fun setActive(sessionId: UUID) : Int {
        val sql = "UPDATE $SESSION_TABLE SET ACTIVE = TRUE WHERE ID = ?;"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.setObject(1, sessionId)
            return preparedStatement.executeUpdate()
        }
    }

    override fun setDeactive() : Int {
        val sql = "UPDATE $SESSION_TABLE SET ACTIVE = FALSE"

        connection.prepareStatement(sql).use { preparedStatement ->
            return preparedStatement.executeUpdate()
        }
    }

    override fun deleteById(id: UUID) : Int {
        val sql = "DELETE FROM $SESSION_TABLE WHERE ID = ?;"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.setObject(1, id)
            return preparedStatement.executeUpdate()
        }
    }

    override fun deleteAll() : Int {
        val sql = "DELETE FROM $SESSION_TABLE"

        connection.prepareStatement(sql).use { preparedStatement ->
            return preparedStatement.executeUpdate()
        }
    }

    override fun deleteActive() : Int {
        val sql = "DELETE FROM $SESSION_TABLE WHERE ACTIVE = TRUE;"

        connection.prepareStatement(sql).use { preparedStatement ->
            return preparedStatement.executeUpdate()
        }
    }
}