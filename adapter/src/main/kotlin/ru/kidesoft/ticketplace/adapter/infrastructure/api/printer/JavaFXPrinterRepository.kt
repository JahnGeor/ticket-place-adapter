package ru.kidesoft.ticketplace.adapter.infrastructure.api.printer;

import ru.kidesoft.ticketplace.adapter.application.port.PrinterPort
import ru.kidesoft.ticketplace.adapter.domain.order.OrderExposed
import ru.kidesoft.ticketplace.adapter.domain.setting.PrintSetting

class JavaFXPrinterRepository: PrinterPort {
    override fun print(setting: PrintSetting, orderExposed: OrderExposed) {
        // TODO("Printer repository of JavaFX not implemented yet")
    }
}
