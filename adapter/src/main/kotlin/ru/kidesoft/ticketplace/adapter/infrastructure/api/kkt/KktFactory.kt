package ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt

import ru.kidesoft.ticketplace.adapter.application.port.KktPort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.KktType
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.setting.KktSetting
import ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt.atol.AtolKktImpl
import java.util.*
import kotlin.math.log

class KktFactory : KktPortFactory {
    var _kktInstance: _KktInstance = _KktInstance()

    override fun getInstance(): KktPort? {
//        if (_kktInstance.kktPort == null) {
//            return null
//        }
//
//        if (_kktInstance.loginId == null || _kktInstance.loginId != loginId) {
//            return null
//        }
//
//        if (_kktInstance.kktType != kktType || _kktInstance.kktPort == null) {
//            return null
//        }

        return _kktInstance.kktPort
    }

    override fun createInstance(kktType: KktType, cashierData : Cashier, kktSetting: KktSetting?): KktPort { // TODO: Добавить сюда сразу пользователя, чтобы каждый раз не лазить в базу данных
        _kktInstance.kktPort?.destroy()

        val setting = kktSetting?: KktSetting.getDefault()

        val kktPort = when(kktType) {
            KktType.ATOL -> AtolKktImpl(cashierData, setting)
        }

        kktPort.openConnection()

        _kktInstance = _KktInstance().apply {
            this.kktPort = kktPort
            this.kktType = kktType
        }

        return kktPort
    }


    // TODO: с заделом на несколько активных сеансов
    override fun deleteInstance(kktType: KktType) {
        _kktInstance.kktPort?.destroy()

        _kktInstance.kktPort = null
        _kktInstance.kktType = null
    }

}

class _KktInstance {
    var kktPort: KktPort? = null
    var kktType: KktType? = null
}
