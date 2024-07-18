package ru.kidesoft.ticketplace.adapter.domain.order

import org.apache.logging.log4j.core.config.Order
import java.lang.Exception
import java.time.ZonedDateTime
import java.util.stream.Collectors
import kotlin.properties.Delegates

class OrderBusinessException(message: String) : Exception(message)

data class OrderExposed(
    var id: Int,
    var paymentType: PaymentType,
    var createdAt: ZonedDateTime,
    var tickets: MutableList<Ticket> = mutableListOf(),
    var cashierName: String? = null
) : Cloneable {
    override fun clone(): OrderExposed {
        return OrderExposed(
            id = this.id,
            paymentType = this.paymentType,
            createdAt = ZonedDateTime.from(this.createdAt),
            tickets = this.tickets.map { it.copy() }.toMutableList(),
            cashierName = this.cashierName
        )
    }

    //var id by Delegates.notNull<Int>()
//
//    var paymentType: PaymentType = PaymentType.UNDEFINED
//    lateinit var createdAt: ZonedDateTime
//    var cashierName: String? = null
//    var tickets: MutableList<Ticket> = mutableListOf()

    fun buildFor(printType: PrintType) : OrderExposed {
        val clone = this.copy()

        return when(printType) {
            PrintType.CHECK -> clone.removeZeroAmountPositions().removeByPaymentType().removeByPrintType(printType)
            PrintType.TICKET -> clone.removeByPrintType(printType)
        }
    }

    fun removeZeroAmountPositions(): OrderExposed {
        tickets.removeIf { it.amount == 0F }
        if (tickets.isEmpty()) throw OrderBusinessException("Ticket can't be empty") else return this
    }

    fun removeByPaymentType(): OrderExposed {
        return when (this.paymentType) {
            PaymentType.CASH, PaymentType.CARD, PaymentType.ACCOUNT_INDIVIDUAL -> this
            else -> throw OrderBusinessException("Illegal payment type: ${this.paymentType}")
        }
    }

    fun removeByPrintType(printType: PrintType) : OrderExposed {
        val statusDescription = this.tickets.map { it.status.value }.distinct().joinToString(", ")

        when (printType) {
            PrintType.CHECK -> tickets.removeIf { it.status != StatusType.PAYED && it.status != StatusType.RETURNED }
            PrintType.TICKET -> tickets.removeIf { it.status != StatusType.PAYED && it.status != StatusType.CREATED }
        }

        if (tickets.isEmpty()) throw OrderBusinessException("Список билетов не может быть нулевым. Список билетов до совершения операции: $statusDescription")

        return this
    }
}