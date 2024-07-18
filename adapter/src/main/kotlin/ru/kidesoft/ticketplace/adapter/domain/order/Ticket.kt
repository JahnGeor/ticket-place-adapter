package ru.kidesoft.ticketplace.adapter.domain.order

import java.time.ZonedDateTime

data class Ticket(
    var id: Int,
    var number: String,
    var status: StatusType,
    var zone: String,
    var seatNumber: Int,
    var rowSector: Int,
    var amount: Float,
    var showName: String,
    var ageLimit: String,
    var dateTime: ZonedDateTime
) : Cloneable {
    override fun clone(): Ticket {
        return Ticket(id, number, status, zone, seatNumber, rowSector, amount, showName, ageLimit, ZonedDateTime.from(dateTime))
    }
}

