package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.setting.KktSetting
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting
import java.util.UUID
import kotlin.math.log

class GetKktPort(commonPort: CommonPort): Usecase<GetKktPort.Input, GetKktPort.Output>(commonPort) {
    class Input(val cashier: Cashier? = null) {}
    class Output(val kktPort: KktPort)

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {

        val kktPort = if (input == null) {
            getByCurrentUser()
        } else {
            input.cashier?.let { getDefault(it) } ?: throw IllegalArgumentException("При переданных входных параметрах обязательно наличие данных по оператору")
        }

        val output = Output(kktPort)

        sceneManager?.let {
            present(output, sceneManager)
        }

        return output
    }

    private fun getByCurrentUser() : KktPort {
        val login = commonPort.databasePort.getLogin().getByCurrent() ?: throw IllegalArgumentException("Не найдены данные авторизации текущего пользователя")
        val setting = commonPort.databasePort.getSetting().getByCurrent() ?: throw IllegalArgumentException("Не найдены данные настроек текущего пользователя")
        val cashier = commonPort.databasePort.getProfile().getCurrentCashier() ?: throw IllegalArgumentException("Не найдены данные профиля текущего пользователя")
        return commonPort.kktPortFactory.getInstance() ?: commonPort.kktPortFactory.createInstance(KktType.ATOL, cashier, setting.kktSetting)
    }

    private fun getByLoginId(loginId : UUID) : KktPort {
         val cashier = commonPort.databasePort.getProfile().getCashierByLoginId(loginId) ?: throw IllegalArgumentException("Не найдены данные профиля указанного пользователя с loginId: $loginId")
         val setting = commonPort.databasePort.getSetting().getByLoginId(loginId) ?: throw IllegalArgumentException("Не найдены данные по настройкам указанного пользователя с loginId: $loginId")

        return kotlin.runCatching {
            commonPort.kktPortFactory.getInstance() ?: commonPort.kktPortFactory.createInstance(KktType.ATOL, cashier, setting.kktSetting)
        }.getOrNull()?:run {
            val defaultSettingKkt = KktSetting.getDefault()
            commonPort.kktPortFactory.getInstance() ?: commonPort.kktPortFactory.createInstance(KktType.ATOL, cashier, defaultSettingKkt)
        }
    }

    private fun getDefault(cashier : Cashier) : KktPort {
        return commonPort.kktPortFactory.createInstance(KktType.ATOL, cashier)
    }

    override fun present(output: Output, sceneManager: SceneManager) {

    }
}