package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import ru.kidesoft.ticketplace.adapter.application.port.SettingPort
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting

class SettingRepository(database: Database) : SettingPort {
    object Settings : UUIDTable("setting") {

    }

    override fun getCurrentSetting(): Setting? {

    }

}