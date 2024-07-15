package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.jetbrains.exposed.sql.Database
import ru.kidesoft.ticketplace.adapter.application.port.SettingPort
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting

class SettingRepository(database: Database) : SettingPort {
    override fun getCurrentSetting(): Setting? {
        return Setting()
    }

}