package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.kidesoft.ticketplace.adapter.application.port.LoginPort
import ru.kidesoft.ticketplace.adapter.application.port.ProfilePort
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.profile.ProfileExposed
import ru.kidesoft.ticketplace.adapter.domain.profile.RoleType
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.LoginRepository.Logins.id
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.LoginRepository.Logins.uniqueIndex
import java.util.*

class ProfileRepository(private val database: Database) : ProfilePort {
    object Profiles : UUIDTable("PROFILE") {
        val inn: Column<Long> = long("INN")
        val loginId: Column<UUID> = uuid("LOGIN_ID").references(LoginRepository.Logins.id)
        val fullName: Column<String> = varchar("FULLNAME", 256)
        val avatar: Column<String> = varchar("AVATAR", 1024)
        val userName: Column<String> = varchar("USERNAME", 1024)
        val userId: Column<Long> = long("USER_ID")
        val roleType: Column<RoleType> = enumeration("ROLE", RoleType::class)
    }

    override fun Create(profile: ProfileExposed): Profile {
        return transaction(database) {
            val id = Profiles.insert {
                it[inn] = profile.cashier.inn
                it[fullName] = profile.cashier.fullName
                it[loginId] = profile.loginId
                it[avatar] = profile.avatar
                it[userName] = profile.userName
                it[userId] = profile.userId
                it[roleType] = profile.roleType
            } get Profiles.id

            Profile().apply {
                this.cashier = profile.cashier
                this.avatar = profile.avatar
                this.userId = profile.userId
                this.loginId = profile.loginId
                this.userName = profile.userName
                this.roleType = profile.roleType
                this.id = id.value
            }
        }
    }

    override fun Update(id: UUID, profile: ProfileExposed): Profile {
        return transaction(database) {

            Profiles.update({ Profiles.id eq id }) {
                it[inn] = profile.cashier.inn
                it[fullName] = profile.cashier.fullName
                it[loginId] = profile.loginId
                it[avatar] = profile.avatar
                it[userName] = profile.userName
                it[userId] = profile.userId
                it[roleType] = profile.roleType
            }

            Profile().apply {
                this.cashier = profile.cashier
                this.avatar = profile.avatar
                this.userId = profile.userId
                this.loginId = profile.loginId
                this.userName = profile.userName
                this.roleType = profile.roleType
                this.id = id
            }
        }
    }

    private fun ResultRow.toProfile() = Profile().apply {
        this.cashier.inn = this@toProfile[Profiles.inn]
        this.cashier.fullName = this@toProfile[Profiles.fullName]
        this.avatar = this@toProfile[Profiles.avatar]
        this.userId = this@toProfile[Profiles.userId]
        this.loginId = this@toProfile[Profiles.loginId]
        this.userName = this@toProfile[Profiles.userName]
        this.roleType = this@toProfile[Profiles.roleType]
        this.id = this@toProfile[Profiles.id].value
    }

    override fun getByLoginId(loginId: UUID): Profile? {
        return transaction {
            Profiles.selectAll().where { Profiles.loginId eq loginId }.map {
                it.toProfile()
            }.singleOrNull()
        }
    }

    override fun getCurrentProfile(): Profile? {
       return transaction {
            Profiles.join(
                SessionRepository.Sessions,
                JoinType.INNER,
                additionalConstraint = { (SessionRepository.Sessions.loginId eq Profiles.loginId) and (SessionRepository.Sessions.active eq true) })
                .selectAll().map {
                    it.toProfile()
                }.singleOrNull()
        }
    }


    override fun getCashierList(): List<Cashier> {
        return transaction(database) {
            val cashiers = mutableListOf<Cashier>()

            Profiles.selectAll().map {
                cashiers.add(Cashier().apply {
                    this.inn = it[Profiles.inn]
                    this.fullName = it[Profiles.fullName]
                })
            }

            cashiers
        }
    }

    override fun getCurrentCashier() : Cashier? {
        return transaction(database) {
            Profiles.selectAll().map {
                Cashier().apply {
                    this.inn = it[Profiles.inn]
                    this.fullName = it[Profiles.fullName]
                }
            }.singleOrNull()
        }
    }

}