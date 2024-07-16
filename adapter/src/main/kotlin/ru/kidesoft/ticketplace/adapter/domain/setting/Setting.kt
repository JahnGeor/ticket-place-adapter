package ru.kidesoft.ticketplace.adapter.domain.setting

import org.h2.mvstore.Page
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.SettingRepository.Settings
import java.util.UUID
import kotlin.properties.Delegates
import kotlin.time.Duration

enum class PageSize {
    A4, A5
}

enum class PageOrientation {
    PORTRAIT, LANDSCAPE
}

class Setting(): SettingExposed() {
    var id by Delegates.notNull<UUID>()

    constructor(settingExposed: SettingExposed) : this() {
        this.loginId = settingExposed.loginId
        this.kkt = settingExposed.kkt
        this.print = settingExposed.print
        this.update = settingExposed.update
        this.server = settingExposed.server
    }

    constructor(id : UUID, settingExposed: SettingExposed) : this(settingExposed) {
        this.id = id
    }

}

open class SettingExposed {
    lateinit var loginId : UUID
    var kkt = KktSetting()
    var server = ServerSetting()
    var update = UpdateSetting()
    var print = PrintSetting()
}

class KktSetting {
    lateinit var path: String
    var autoRecconect by Delegates.notNull<Boolean>()
}

class UpdateSetting {
    var isAuto by Delegates.notNull<Boolean>()
    lateinit var repositoryUrl: String
}

class PrintSetting {
    var isPrintingCheck by Delegates.notNull<Boolean>()
    var isPrintingTicket by Delegates.notNull<Boolean>()
    lateinit var pageSize: PageSize
    lateinit var pageOrientation: PageOrientation
    lateinit var printerName: String
}

class ServerSetting {
    lateinit var requestInterval: java.time.Duration
    lateinit var requestTimeout: java.time.Duration
}

