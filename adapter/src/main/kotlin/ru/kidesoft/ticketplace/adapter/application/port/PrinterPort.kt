package ru.kidesoft.ticketplace.adapter.application.port

import ru.kidesoft.ticketplace.adapter.domain.order.Order
import ru.kidesoft.ticketplace.adapter.domain.setting.PrintSetting

interface PrinterPort {
    fun print(setting : PrintSetting, order: Order)
}