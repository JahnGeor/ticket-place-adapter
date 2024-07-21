package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import ru.kidesoft.ticketplace.adapter.application.port.ProfilePort
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.profile.ProfileExposed
import ru.kidesoft.ticketplace.adapter.domain.profile.RoleType
import java.util.*

class ProfileRepository(private val database: Database) : ProfilePort {
    object ProfileTable : UUIDTable("PROFILE") {
        val inn: Column<Long> = long("INN")
        val loginId: Column<UUID> = uuid("LOGIN_ID").references(LoginRepository.LoginTable.id)
        val fullName: Column<String> = varchar("FULLNAME", 256)
        val avatar: Column<String> = varchar("AVATAR", 1024)
        val userName: Column<String> = varchar("USERNAME", 1024)
        val userId: Column<Long> = long("USER_ID")
        val roleType: Column<RoleType> = enumeration("ROLE", RoleType::class)
    }

    override fun create(profile: ProfileExposed): Profile {
        return transaction(database) {
            val id = ProfileTable.insert {
                it[inn] = profile.cashier.inn
                it[fullName] = profile.cashier.fullName
                it[loginId] = profile.loginId
                it[avatar] = profile.avatar
                it[userName] = profile.userName
                it[userId] = profile.userId
                it[roleType] = profile.roleType
            } get ProfileTable.id

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

    override fun update(id: UUID, profile: ProfileExposed): Profile {
        return transaction(database) {

            ProfileTable.update({ ProfileTable.id eq id }) {
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
        this.cashier.inn = this@toProfile[ProfileTable.inn]
        this.cashier.fullName = this@toProfile[ProfileTable.fullName]
        this.avatar = this@toProfile[ProfileTable.avatar]
        this.userId = this@toProfile[ProfileTable.userId]
        this.loginId = this@toProfile[ProfileTable.loginId]
        this.userName = this@toProfile[ProfileTable.userName]
        this.roleType = this@toProfile[ProfileTable.roleType]
        this.id = this@toProfile[ProfileTable.id].value
    }

    override fun getByLoginId(loginId: UUID): Profile? {
        return transaction {
            ProfileTable.selectAll().where { ProfileTable.loginId eq loginId }.map {
                it.toProfile()
            }.singleOrNull()
        }
    }

    override fun getByCurrent(): Profile? {
       return transaction {
            ProfileTable.join(
                SessionRepository.SessionTable,
                JoinType.INNER,
                additionalConstraint = { (SessionRepository.SessionTable.loginId eq ProfileTable.loginId) and (SessionRepository.SessionTable.active eq true) })
                .selectAll().map {
                    it.toProfile()
                }.singleOrNull()
        }
    }


    override fun getCashierList(): List<Cashier> {
        return transaction(database) {
            val cashiers = mutableListOf<Cashier>()

            ProfileTable.selectAll().map {
                cashiers.add(Cashier().apply {
                    this.inn = it[ProfileTable.inn]
                    this.fullName = it[ProfileTable.fullName]
                })
            }

            cashiers
        }
    }

    override fun getCurrentCashier() : Cashier? {
        return transaction {
            ProfileTable.join(
                SessionRepository.SessionTable,
                JoinType.INNER,
                additionalConstraint = { (SessionRepository.SessionTable.loginId eq ProfileTable.loginId) and (SessionRepository.SessionTable.active eq true) })
                .selectAll().map {
                    Cashier().apply {
                        this.inn = it[ProfileTable.inn]
                        this.fullName = it[ProfileTable.fullName]
                    }
                }.singleOrNull()
        }
    }

}