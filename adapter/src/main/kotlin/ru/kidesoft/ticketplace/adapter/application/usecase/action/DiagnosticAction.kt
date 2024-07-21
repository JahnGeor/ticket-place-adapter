package ru.kidesoft.ticketplace.adapter.application.usecase.action

import ru.kidesoft.ticketplace.adapter.application.port.ApiPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.StartKktSession
import ru.kidesoft.ticketplace.adapter.application.usecase.updater.UpdateMain
import ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt.KktFactory

class DiagnosticAction(commonPort: CommonPort): Usecase<DiagnosticAction.Input, DiagnosticAction.Output>(commonPort) {
    class Input : Usecase.Input
    class Output : Usecase.Output

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val output = Output()

        StartKktSession(commonPort).invoke()

        UpdateMain(commonPort)

        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.showNotification(NotificationType.INFO, "Соединения восстановлены", "Диагностика завершена")
    }

}