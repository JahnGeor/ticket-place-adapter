package ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt

import ru.kidesoft.ticketplace.adapter.application.port.KktPort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.KktType
import ru.kidesoft.ticketplace.adapter.domain.setting.KktSetting
import ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt.atol.AtolKktImpl
import java.util.*
import kotlin.math.log

class KktFactory : KktPortFactory {
    var _kktInstance: _KktInstance = _KktInstance()

    override fun getInstance(kktType: KktType, loginId: UUID): KktPort? {
        if (_kktInstance.kktPort == null) {
            return null
        }

        if (_kktInstance.loginId == null || _kktInstance.loginId != loginId) {
            return null
        }

        if (_kktInstance.kktType != kktType || _kktInstance.kktPort == null) {
            return null
        }

        return _kktInstance.kktPort
    }

    override fun createInstance(kktType: KktType, kktSetting: KktSetting, loginId: UUID): KktPort {
        val kktPort = when(kktType) {
            KktType.ATOL -> AtolKktImpl()
        }

        _kktInstance = _KktInstance().apply {
            this.kktPort = kktPort
            this.kktType = kktType
            this.loginId = loginId
        }

        return kktPort
    }


}

class _KktInstance {
    var kktPort: KktPort? = null
    var loginId: UUID? = null
    var kktType: KktType? = null
}
