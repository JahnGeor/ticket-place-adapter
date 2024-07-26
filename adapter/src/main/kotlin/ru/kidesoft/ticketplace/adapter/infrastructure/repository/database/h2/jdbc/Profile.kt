package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.jdbc

import ru.kidesoft.ticketplace.adapter.application.port.ProfilePort
import ru.kidesoft.ticketplace.adapter.application.port.SessionPort
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.profile.ProfileInfo
import ru.kidesoft.ticketplace.adapter.domain.profile.RoleType
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class ProfileRepositoryJdbc(val connection: Connection, val sessionPort: SessionPort) : ProfilePort {

    fun ResultSet.toProfile(): Profile = Profile(
        this.getObject("ID", UUID::class.java),
        this.getObject("LOGIN_ID", UUID::class.java),
        this.getString("FULLNAME"),
        this.getLong("INN"),
        this.getString("AVATAR"),
        this.getString("USERNAME"),
        this.getInt("USER_ID"),
        RoleType.entries.find { it.ordinal == this.getInt("ROLE") } ?: RoleType.UNDEFINED
    )

    fun ResultSet.toCashier(): Cashier = Cashier(
        this.getString("FULLNAME"),
        this.getLong("INN"),
    )


    override fun getCashierList(): List<Cashier> {
        val sql = "SELECT FULLNAME, INN FROM $PROFILE_TABLE"

        connection.prepareStatement(sql).use { statement ->
            statement.executeQuery().use { resultSet ->

                val cashierList = mutableListOf<Cashier>()

                while (resultSet.next()) {
                    cashierList.add(resultSet.toCashier())
                }

                return cashierList
            }
        }
    }

    override fun saveByLoginId(loginId: UUID, profileInfo: ProfileInfo): UUID {
        // val loginId = sessionPort.getActive()?.loginId ?: throw IllegalArgumentException("No active session")

        val sql =
            "SELECT ID FROM FINAL TABLE (MERGE INTO PROFILE(LOGIN_ID, INN, ROLE, AVATAR, FULLNAME, USERNAME, USER_ID) KEY(LOGIN_ID) VALUES(?, ?, ?, ?, ?, ?, ?))"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.setObject(1, loginId)
            preparedStatement.setLong(2, profileInfo.inn)
            preparedStatement.setInt(3, profileInfo.roleType.ordinal)
            preparedStatement.setString(4, profileInfo.avatar)
            preparedStatement.setString(5, profileInfo.fullName)
            preparedStatement.setString(6, profileInfo.userName)
            preparedStatement.setInt(7, profileInfo.userId)

            preparedStatement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    return resultSet.getObject("ID", UUID::class.java)
                } else throw SQLException("Profile save action return no id")
            }
        }
    }

    override fun getByLoginId(loginId: UUID): Profile? {
        val sql = "SELECT * FROM $PROFILE_TABLE WHERE LOGIN_ID = ?"

        connection.prepareStatement(sql).use { statement ->
            statement.setObject(1, loginId)
            statement.executeQuery().use { resultSet ->
                return if (resultSet.next()) {
                    resultSet.toProfile()
                } else {
                    return null
                }

            }
        }
    }

    override fun getByCurrent(): Profile? {
        val loginId = sessionPort.getActive()?.loginId ?: throw IllegalArgumentException("No active session")
        return getByLoginId(loginId)
    }

    override fun getCurrentCashier(): Cashier? {
        val loginId = sessionPort.getActive()?.loginId ?: throw IllegalArgumentException("No active session")
        return getCashierByLoginId(loginId)
    }

    override fun getCashierByLoginId(loginId: UUID): Cashier? {
        val sql = "SELECT FULLNAME, INN FROM $PROFILE_TABLE WHERE LOGIN_ID = ? LIMIT 1"

        connection.prepareStatement(sql).use { preparedStatement ->
            preparedStatement.setObject(1, loginId)
            preparedStatement.executeQuery().use { resultSet ->
                return if (resultSet.next()) resultSet.toCashier() else null
            }
        }
    }
}