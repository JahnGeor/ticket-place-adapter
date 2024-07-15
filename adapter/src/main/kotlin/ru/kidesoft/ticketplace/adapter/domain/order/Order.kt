package ru.kidesoft.ticketplace.adapter.domain.order

import java.time.ZonedDateTime
import kotlin.properties.Delegates

class Order {
    var id by Delegates.notNull<Int>()
    lateinit var sourceType: SourceType
    lateinit var paymentType: PaymentType
    lateinit var createdAt: ZonedDateTime
    lateinit var cashierName: String
    lateinit var tickets : Ticket
    lateinit var operationType: OperationType
}