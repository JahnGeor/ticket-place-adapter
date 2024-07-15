package ru.kidesoft.ticketplace.adapter.domain.history

import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.time.ZonedDateTime
import java.util.UUID
import kotlin.properties.Delegates

class History() {
    lateinit var id : UUID
    lateinit var loginId : UUID
    var orderId by Delegates.notNull<Int>()
    lateinit var createdAt : ZonedDateTime
    lateinit var error : String
    lateinit var statusType : StatusType
    lateinit var reasonType : ReasonType
    lateinit var sourceType : SourceType
}
