package ru.kidesoft.ticketplace.adapter.domain.history

import ru.kidesoft.ticketplace.adapter.domain.order.OperationType
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.time.ZonedDateTime
import java.util.UUID

data class History(var id: UUID, var loginId: UUID, var orderId: Int, var stepType: Step, var historyExposed: HistoryInfo)

data class HistoryInfo(var sourceType: SourceType, var createdAt: ZonedDateTime, var error: String, var errorStatus: ErrorStatus, var operationType: OperationType)