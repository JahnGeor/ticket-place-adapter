package ru.kidesoft.ticketplace.adapter.application.usecase.action

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.GetKktPort

class PrintXReportAction(commonPort: CommonPort) : Usecase<PrintXReportAction.Input, PrintXReportAction.Output>(commonPort) {
    class Input
    class Output

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        GetKktPort(commonPort).invoke().kktPort.printXReport()

        val output = Output()

        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.showNotification(NotificationType.INFO, "Операция с кассой", "Х-отчет успешно напечатан")
    }
}