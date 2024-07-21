package ru.kidesoft.ticketplace.adapter.application.port

import ru.kidesoft.ticketplace.adapter.domain.order.OrderExposed
import ru.kidesoft.ticketplace.adapter.domain.setting.PrintSetting
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting

interface PrinterPort {
    fun print(setting : PrintSetting, orderExposed: OrderExposed)
}