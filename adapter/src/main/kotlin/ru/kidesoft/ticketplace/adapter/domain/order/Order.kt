package ru.kidesoft.ticketplace.adapter.domain.order

import ru.kidesoft.ticketplace.adapter.domain.exception.BusinessException
import java.lang.Exception
import java.time.ZonedDateTime

data class Order(
    var id: Int,
    var paymentType: PaymentType,
    var createdAt: ZonedDateTime,
    var tickets: MutableList<Ticket> = mutableListOf(),
    var cashierName: String? = null
) : Cloneable {
    override fun clone(): Order {
        return Order(
            id = id,
            paymentType = paymentType,
            createdAt = ZonedDateTime.from(createdAt),
            tickets = tickets.map { it.copy() }.toMutableList(),
            cashierName = cashierName
        )
    }


    fun buildFor(printType: PrintType) : Order {
        val clone = this.copy()

        return when(printType) {
            PrintType.CHECK -> clone.removeZeroAmountPositions().removeByPaymentType().removeByPrintType(printType)
            PrintType.TICKET -> clone.removeByPrintType(printType)
        }
    }

    private fun removeZeroAmountPositions(): Order {
        tickets.removeIf { it.amount == 0F }
        if (tickets.isEmpty()) throw BusinessException("Отсутствуют билеты в заказе") else return this
    }

    private fun removeByPaymentType(): Order {
        return when (this.paymentType) {
            PaymentType.CASH, PaymentType.CARD, PaymentType.ACCOUNT_INDIVIDUAL -> this
            else -> throw BusinessException("Необрабатываемая форма оплаты: ${this.paymentType}")
        }
    }

    private fun removeByPrintType(printType: PrintType) : Order {
        val statusDescription = this.tickets.map { it.status.value }.distinct().joinToString(", ")

        when (printType) {
            PrintType.CHECK -> tickets.removeIf { it.status != StatusType.PAYED && it.status != StatusType.RETURNED }
            PrintType.TICKET -> tickets.removeIf { it.status != StatusType.PAYED && it.status != StatusType.CREATED }
        }

        if (tickets.isEmpty()) throw BusinessException("Список билетов не может быть нулевым. Список билетов до совершения операции: $statusDescription")

        return this
    }
}