package ru.kidesoft.ticketplace.adapter.domain.setting

import net.datafaker.providers.base.Bool
import ru.kidesoft.ticketplace.adapter.domain.session.SessionInfo
import java.time.Duration
import java.util.UUID
import kotlin.properties.Delegates

enum class PageSize {
    A4, A5, UNDEFINED
}

enum class PageOrientation {
    PORTRAIT, LANDSCAPE, UNDEFINED
}

data class Setting(
    val id: UUID,
    val loginId: UUID,
    val kktSetting: KktSetting,
    val serverSetting: ServerSetting,
    val updateSetting: UpdateSetting,
    val print: PrintSetting
) {
    constructor(id: UUID, loginId: UUID, settingInfo: SettingInfo) : this(
        id,
        loginId,
        settingInfo.kkt,
        settingInfo.server,
        settingInfo.update,
        settingInfo.print
    )
}

data class SettingInfo(
    val kkt: KktSetting,
    val server: ServerSetting,
    val update: UpdateSetting,
    val print: PrintSetting
) {
    companion object {
        fun getDefault(): SettingInfo {
            return SettingInfo(
                KktSetting.getDefault(),
                ServerSetting.getDefault(),
                UpdateSetting.getDefault(),
                PrintSetting.getDefault())
        }
    }
}

data class KktSetting(val path: String, val autoReconnect: Boolean, val printTimeCheck: Boolean) {
    companion object {
        fun getDefault(): KktSetting = KktSetting(path = "./driver/kkt/atol", autoReconnect = true, printTimeCheck = false)
    }
}

class UpdateSetting(val isAuto: Boolean, val updateUrl: String) {
    companion object {
        fun getDefault(): UpdateSetting = UpdateSetting(isAuto = false, updateUrl = "")
    }
}

class PrintSetting(
    val isPrintingCheck: Boolean,
    val isPrintingTicket: Boolean,
    val pageSize: PageSize,
    val pageOrientation: PageOrientation,
    val printerName: String
) {
    companion object {
        fun getDefault(): PrintSetting = PrintSetting(
            isPrintingCheck = true,
            isPrintingTicket = false,
            pageSize = PageSize.A4,
            pageOrientation = PageOrientation.LANDSCAPE,
            printerName = ""
        )
    }
}

class ServerSetting(val requestInterval: java.time.Duration, val requestTimeout: java.time.Duration) {
    companion object {
        fun getDefault(): ServerSetting =
            ServerSetting(requestInterval = Duration.ofSeconds(5), requestTimeout = Duration.ofSeconds(30))
    }
}
