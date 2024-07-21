package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import ru.kidesoft.ticketplace.adapter.application.port.ApiPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase

class PrintLastReceipt(commonPort: CommonPort) : Usecase<PrintLastReceipt.Input, PrintLastReceipt.Output>(commonPort) {
    class Input : Usecase.Input
    class Output : Usecase.Output

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val kktPort = GetCurrentKktPort(commonPort).invoke().kktPort

        val output = Output()

        kktPort.printLastReceipt()

        sceneManager?.let {
            present(output, it)
        }

        return output

    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.showNotification(NotificationType.INFO, "Операция с кассой", "Печать последнего чека успешно выполнена")
    }


}