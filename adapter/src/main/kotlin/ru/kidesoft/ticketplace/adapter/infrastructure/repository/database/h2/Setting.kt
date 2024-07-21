package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.javatime.duration
import org.jetbrains.exposed.sql.transactions.transaction
import ru.kidesoft.ticketplace.adapter.application.port.SettingPort
import ru.kidesoft.ticketplace.adapter.domain.setting.PageOrientation
import ru.kidesoft.ticketplace.adapter.domain.setting.PageSize
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting
import ru.kidesoft.ticketplace.adapter.domain.setting.SettingExposed
import java.time.Duration
import java.util.*

class SettingRepository(database: Database) : SettingPort {

    /**
     *
     *
     * create table SETTING
     * (
     *     ID                      UUID              not null,
     *     KKT_AUTO_RECONNECT      BOOLEAN           not null,
     *     KKT_DRIVER_PATH         CHARACTER VARYING not null,
     *     PRINTER_NAME            CHARACTER VARYING,
     *     PAGE_SIZE               TINYINT           not null,
     *     PAGE_ORIENTATION        TINYINT           not null,
     *     PRINT_CHECK             BOOLEAN           not null,
     *     PRINT_TICKET            BOOLEAN           not null,
     *     UPDATE_REPOSITORY_URL   CHARACTER VARYING not null,
     *     UPDATE_AUTOMATICALLY    BOOLEAN           not null,
     *     SERVER_REQUEST_TIMEOUT  NUMERIC           not null,
     *     SERVER_REQUEST_INTERVAL NUMERIC           not null,
     *     LOGIN_ID                UUID              not null,
     *     constraint SETTING_PK
     *         primary key (ID),
     *     constraint SETTING_LOGIN_ID_FK
     *         foreign key (LOGIN_ID) references LOGIN
     *             on update cascade on delete cascade
     * );
     *
     *
     */

    object SettingTable : UUIDTable("SETTING") {
        val kktAutoRecconect = bool("KKT_AUTO_RECONNECT").default(false)
        val kktDriverPath = text("KKT_DRIVER_PATH").default("./driver/kkt/atol")
        val printerName = text("PRINTER_NAME").default("")
        val pageSize = enumeration<PageSize>("PAGE_SIZE").default(PageSize.A4)
        val pageOrientation = enumeration<PageOrientation>("PAGE_ORIENTATION").default(PageOrientation.PORTRAIT)
        val printCheck = bool("PRINT_CHECK").default(true)
        val printTicket = bool("PRINT_TICKET").default(false)
        val updateRepositoryUrl = text("UPDATE_REPOSITORY_URL").default("")
        val updateAutomatically = bool("UPDATE_AUTOMATICALLY").default(false)
        val serverRequestTimeout = duration("SERVER_REQUEST_TIMEOUT").default(Duration.ofSeconds(30))
        val serverRequestInterval = duration("SERVER_REQUEST_INTERVAL").default(Duration.ofSeconds(5))
        val loginId = uuid("LOGIN_ID").references(LoginRepository.LoginTable.id)
    }

    fun ResultRow.toEntity() : Setting {
        return Setting().apply {
            id = this@toEntity[SettingTable.id].value
            loginId = this@toEntity[SettingTable.loginId]
            kkt.path = this@toEntity[SettingTable.kktDriverPath]
            kkt.autoRecconect = this@toEntity[SettingTable.kktAutoRecconect]
            server.requestInterval = this@toEntity[SettingTable.serverRequestInterval]
            server.requestTimeout = this@toEntity[SettingTable.serverRequestTimeout]
            print.isPrintingCheck = this@toEntity[SettingTable.printCheck]
            print.isPrintingTicket = this@toEntity[SettingTable.printTicket]
            print.pageSize = this@toEntity[SettingTable.pageSize]
            print.pageOrientation = this@toEntity[SettingTable.pageOrientation]
            print.printerName = this@toEntity[SettingTable.printerName]
            update.isAuto = this@toEntity[SettingTable.updateAutomatically]
            update.repositoryUrl = this@toEntity[SettingTable.updateRepositoryUrl]
        }
    }

    override fun create(setting: SettingExposed) : Setting  {
        return transaction {
            val id = SettingTable.insert {
                it[SettingTable.loginId] = setting.loginId
                it[SettingTable.updateAutomatically] = setting.update.isAuto
                it[SettingTable.updateRepositoryUrl] = setting.update.repositoryUrl
                it[SettingTable.printCheck] = setting.print.isPrintingCheck
                it[SettingTable.printTicket] = setting.print.isPrintingTicket
                it[SettingTable.pageSize] = setting.print.pageSize
                it[SettingTable.pageOrientation] = setting.print.pageOrientation
                it[SettingTable.kktDriverPath] = setting.kkt.path
                it[SettingTable.kktAutoRecconect] = setting.kkt.autoRecconect
                it[SettingTable.printerName] = setting.print.printerName
                it[SettingTable.serverRequestInterval] = setting.server.requestInterval
                it[SettingTable.serverRequestTimeout] = setting.server.requestTimeout
            } get SettingTable.id

            Setting(id.value, setting)
        }
    }

    override fun update(id : UUID, setting: SettingExposed) : Setting {
        return transaction  {
            SettingTable.update ({ SettingTable.id eq id } ) {
                it[SettingTable.loginId] = setting.loginId
                it[SettingTable.updateAutomatically] = setting.update.isAuto
                it[SettingTable.updateRepositoryUrl] = setting.update.repositoryUrl
                it[SettingTable.printCheck] = setting.print.isPrintingCheck
                it[SettingTable.printTicket] = setting.print.isPrintingTicket
                it[SettingTable.pageSize] = setting.print.pageSize
                it[SettingTable.pageOrientation] = setting.print.pageOrientation
                it[SettingTable.kktDriverPath] = setting.kkt.path
                it[SettingTable.kktAutoRecconect] = setting.kkt.autoRecconect
                it[SettingTable.printerName] = setting.print.printerName
                it[SettingTable.serverRequestInterval] = setting.server.requestInterval
                it[SettingTable.serverRequestTimeout] = setting.server.requestTimeout
            }

            Setting().apply {
                this.id = id
                this.loginId = setting.loginId
                this.update = setting.update
                this.kkt = setting.kkt
                this.print = setting.print
                this.server = setting.server
            }
        }
    }

    override fun getByLoginId(loginId : UUID) : Setting? {
        return transaction {
            SettingTable.selectAll().where { SettingTable.loginId eq loginId }.map { it.toEntity() }.singleOrNull()
        }
    }

    override fun getById(id : UUID) : Setting? {
        return transaction {
            SettingTable.selectAll().where { SettingTable.id eq id }.map { it.toEntity() }.singleOrNull()
        }
    }

    override fun getByCurrent(): Setting? {
        return transaction {
            SettingTable.join(
                SessionRepository.SessionTable,
                JoinType.INNER,
                additionalConstraint = { (SessionRepository.SessionTable.loginId eq SettingTable.loginId) and (SessionRepository.SessionTable.active eq true) })
                .selectAll().map {
                    it.toEntity()
                }.singleOrNull()
        }
    }

    override fun createDefault(loginId: UUID): Setting {
        return transaction {

            val id = SettingTable.insert {
                it[SettingTable.loginId] = loginId
            } get SettingTable.id

            SettingTable.selectAll().where { SettingTable.id eq id }.map{ it.toEntity() }.single()
        }
    }

}