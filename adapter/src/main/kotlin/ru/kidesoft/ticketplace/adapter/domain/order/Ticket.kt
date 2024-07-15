package ru.kidesoft.ticketplace.adapter.domain.order

import java.time.ZonedDateTime
import kotlin.properties.Delegates

class Ticket {
    var id by Delegates.notNull<Int>()
    lateinit var number: String
    lateinit var status: StatusType
    lateinit var zone : String
    var seatNumber by Delegates.notNull<Int>()
    var rowSector by Delegates.notNull<Int>()
    var amount by Delegates.notNull<Float>()
    lateinit var showName : String
    lateinit var ageLimit : String
    lateinit var dateTime : ZonedDateTime
}

