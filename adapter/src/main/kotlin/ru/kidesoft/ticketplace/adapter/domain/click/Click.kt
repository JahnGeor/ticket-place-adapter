package ru.kidesoft.ticketplace.adapter.domain.click

import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.time.ZonedDateTime
import java.util.UUID

data class Click(val id: UUID, val orderId: Int, val clickId: Int, val sourceType: SourceType, val createdAt: ZonedDateTime) {
    constructor(id: UUID, clickInfo: ClickInfo) : this(id, clickInfo.orderId, clickInfo.clickId, clickInfo.sourceType, clickInfo.createdAt)

    fun toClickInfo() : ClickInfo {
        return ClickInfo(clickId, orderId, sourceType, createdAt)
    }
}

data class ClickInfo(val clickId: Int, val orderId: Int, val sourceType: SourceType, val createdAt: ZonedDateTime)