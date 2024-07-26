package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import java.util.UUID

class PrintLastReceipt(commonPort: CommonPort) : Usecase<PrintLastReceipt.Input, PrintLastReceipt.Output>(commonPort) {
    class Input(val cashier: Cashier) {

    }
    class Output

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val cashier = input?.cashier

        val kktPort = if (cashier == null) {
            GetKktPort(commonPort).invoke().kktPort
        } else {
            GetKktPort(commonPort).invoke(GetKktPort.Input(cashier)).kktPort
        }

        val output = Output()

        kktPort.printLastReceipt()

        if (cashier != null) {
            commonPort.kktPortFactory.deleteInstance(KktType.ATOL)
        }

        sceneManager?.let {
            present(output, it)
        }

        return output

    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.showNotification(NotificationType.INFO, "Операция с кассой", "Печать последнего чека успешно выполнена")
    }


}