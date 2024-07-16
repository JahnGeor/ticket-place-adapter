package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.duration
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
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
    object Settings : UUIDTable("SETTING") {
        val kktAutoRecconect = bool("KKT_AUTO_RECONNECT").default(false)
        val kktDriverPath = text("KKT_DRIVER_PATH").default("./driver/kkt")
        val printerName = text("PRINTER_NAME").default("")
        val pageSize = enumeration<PageSize>("PAGE_SIZE").default(PageSize.A4)
        val pageOrientation = enumeration<PageOrientation>("PAGE_ORIENTATION").default(PageOrientation.PORTRAIT)
        val printCheck = bool("PRINT_CHECK").default(false)
        val printTicket = bool("PRINT_TICKET").default(false)
        val updateRepositoryUrl = text("UPDATE_REPOSITORY_URL").default("")
        val updateAutomatically = bool("UPDATE_AUTOMATICALLY").default(false)
        val serverRequestTimeout = duration("SERVER_REQUEST_TIMEOUT").default(Duration.ZERO)
        val serverRequestInterval = duration("SERVER_REQUEST_INTERVAL").default(Duration.ZERO)
        val loginId = uuid("LOGIN_ID").references(LoginRepository.Logins.id)
    }

    fun ResultRow.toEntity() : Setting {

    }

    override fun create(setting: SettingExposed) : Setting  {

    }

    override fun update(id : UUID, setting: SettingExposed) : Setting {
        return transaction  {
            Settings.update ({ Settings.id eq id } ) {
                it[Settings.loginId] = setting.loginId
                it[Settings.updateAutomatically] = setting.update.isAuto
                it[Settings.updateRepositoryUrl] = setting.update.repositoryUrl
                it[Settings.printCheck] = setting.print.isPrintingCheck
                it[Settings.printTicket] = setting.print.isPrintingTicket
                it[Settings.pageSize] = setting.print.pageSize
                it[Settings.pageOrientation] = setting.print.pageOrientation
                it[Settings.kktDriverPath] = setting.kkt.path
                it[Settings.kktAutoRecconect] = setting.kkt.autoRecconect
                it[Settings.printerName] = setting.print.printerName
                it[Settings.serverRequestInterval] = setting.server.requestInterval
                it[Settings.serverRequestTimeout] = setting.server.requestTimeout
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

    override fun getCurrentSetting(): Setting? {

    }

}