package ru.kidesoft.ticketplace.adapter.application.usecase.action.main

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.core.PoolingServiceControl
import ru.kidesoft.ticketplace.adapter.application.usecase.core.StartSession
import ru.kidesoft.ticketplace.adapter.application.usecase.core.StopSession
import ru.kidesoft.ticketplace.adapter.application.usecase.updater.UpdateMain

class DiagnosticAction(commonPort: CommonPort) : Usecase<DiagnosticAction.Input, DiagnosticAction.Output>(commonPort) {
    class Input
    class Output(val result: Boolean, val reason: String? = null)

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val output = kotlin.runCatching {
            StartSession(commonPort).invoke()
            PoolingServiceControl(commonPort).invoke(PoolingServiceControl.PoolingCommand.RESTART)
        }.fold(
            onSuccess = { Output(true) },
            onFailure = {
                Output(
                false, it.message)
            }
        )

        if (!output.result) {
            StopSession(commonPort).invoke()
        }

        UpdateMain(commonPort).invoke(sceneManager = sceneManager)

        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        if (output.result) {
            sceneManager.showNotification(NotificationType.INFO, "Диагностика", "Диагностика системы завершена успешно")
        } else {
            sceneManager.showNotification(
                NotificationType.ERROR,
                "Диагностика",
                "Диагностика системы завершена неуспешно"
            )
        }

    }

}