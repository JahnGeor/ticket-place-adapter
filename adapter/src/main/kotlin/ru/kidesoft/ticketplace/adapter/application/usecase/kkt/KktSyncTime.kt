package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import java.time.ZonedDateTime

class KktSyncTime(commonPort: CommonPort) : Usecase<KktSyncTime.Input, KktSyncTime.Output>(commonPort) {
    data class Output(var time: ZonedDateTime)
    class Input

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
//        val login = commonPort.databasePort.getLogin().getByCurrent() ?: throw IllegalArgumentException("Не найдены данные авторизации текущего пользователя")
//        val setting = commonPort.databasePort.getSetting().getByCurrent() ?: throw IllegalArgumentException("Не найдены данные настроек текущего пользователя")
//        val cashier = commonPort.databasePort.getProfile().getCurrentCashier() ?: throw IllegalArgumentException("Не найдены данные оператора текущего пользователя")

        val kktPort = GetKktPort(commonPort).invoke().kktPort

        val currentTime = ZonedDateTime.now()

        kktPort.setTime(currentTime)

        val output = Output(currentTime)

        sceneManager?.let {
            present(output, sceneManager)
        }

        return output

        // TODO: Sync method
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.showNotification(NotificationType.INFO, "Синхронизация времени ККТ", "Текущее время кассы ККТ синхронизировано")
    }
}