package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase

class GetCurrentKktPort(commonPort: CommonPort): Usecase<GetCurrentKktPort.Input, GetCurrentKktPort.Output>(commonPort) {
    class Input
    class Output(val kktPort: KktPort)

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val login = commonPort.databasePort.getLogin().getByCurrent() ?: throw IllegalArgumentException("Не найдены данные авторизации текущего пользователя")
        val setting = commonPort.databasePort.getSetting().getByCurrent() ?: throw IllegalArgumentException("Не найдены данные настроек текущего пользователя")
        val profile = commonPort.databasePort.getProfile().getByCurrent() ?: throw IllegalArgumentException("Не найдены данные профиля текущего пользователя")

        var kktPort = commonPort.kktPortFactory.getInstance(KktType.ATOL, login.id) ?: commonPort.kktPortFactory.createInstance(KktType.ATOL, profile.cashier, setting.kkt, login.id)

        val output = Output(kktPort)

        sceneManager?.let {
            present(output, sceneManager)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {

    }
}