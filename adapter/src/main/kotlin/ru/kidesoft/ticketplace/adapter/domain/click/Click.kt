package ru.kidesoft.ticketplace.adapter.domain.click

import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.util.UUID

data class Click(val id: UUID, val orderId: Int, val clickId: Int, val sourceType: SourceType) {
    constructor(id: UUID, clickInfo: ClickInfo) : this(id, clickInfo.orderId, clickInfo.clickId, clickInfo.sourceType)

    fun toClickInfo() : ClickInfo {
        return ClickInfo(clickId, orderId, sourceType)
    }
}

data class ClickInfo(val clickId: Int, val orderId: Int, val sourceType: SourceType)