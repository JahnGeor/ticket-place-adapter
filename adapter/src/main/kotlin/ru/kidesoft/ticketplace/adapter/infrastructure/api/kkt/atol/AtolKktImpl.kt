package ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt.atol

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import ru.atol.drivers10.fptr.Fptr
import ru.atol.drivers10.fptr.IFptr
import ru.atol.drivers10.fptr.IFptr.*
import ru.kidesoft.ticketplace.adapter.application.port.KktPort
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.order.OperationType
import ru.kidesoft.ticketplace.adapter.domain.order.Order
import ru.kidesoft.ticketplace.adapter.domain.order.PaymentType
import ru.kidesoft.ticketplace.adapter.domain.order.Ticket
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.setting.KktSetting
import java.io.File
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AtolKktImpl(val cashier : Cashier) : KktPort {
    private var ifptr : IFptr? = null

    constructor(cashier: Cashier, kktSetting: KktSetting) : this(cashier) {
        setConnection(kktSetting)
    }

    override fun setConnection(kktSetting: KktSetting?) {
        var driverDir = kktSetting?.let {
            val driverDir = File(kktSetting.path)

            if (!driverDir.exists() || !driverDir.isDirectory) {
                throw IllegalArgumentException("${driverDir.path} does not exist or isn't a directory")
            }

            driverDir
        }?: null

        ifptr?.let { it.destroy() }

        ifptr = driverDir?.let { Fptr(it.canonicalPath) } ?: Fptr()

        kktSetting?.let {
            ifptr!!.setSingleSetting(IFptr.LIBFPTR_SETTING_AUTO_RECONNECT, it.autoReconnect.toString())
            ifptr!!.applySingleSettings()
        }
    }

    fun setOperator() {
        ifptr!!.setParam(1021, cashier.fullName)
        ifptr!!.setParam(1203, cashier.inn.toString())
        ifptr!!.operatorLogin().takeIf { it != 0 }?.let { _processError() }
    }

    override fun destroy() {
        ifptr?.destroy()
    }

    override fun getConnection(): Boolean {
        ifptr!!.setParam(LIBFPTR_PARAM_DATA_TYPE, LIBFPTR_DT_STATUS);

        ifptr!!.queryData()

        val isOpened = ifptr!!.getParamBool(LIBFPTR_PARAM_PRINTER_CONNECTION_LOST)

        return ifptr!!.isOpened && !isOpened
    }

    override fun openConnection() {
        ifptr!!.open().takeIf { it != 0 }?.let {
            _processError()
        }
    }

    override fun closeConnection() {
        ifptr!!.close().takeIf { it != 0 }?.let {
            _processError()
        }
    }

    override fun openShift() {
        setOperator()

        ifptr!!.openShift().takeIf { it != 0 }?.let {
            _processError()
        }
    }

    override fun closeShift() {
        setOperator()

        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_CLOSE_SHIFT)
        ifptr!!.report().takeIf { it != 0 }?.let {
            _processError()
        }
        ifptr!!.checkDocumentClosed().takeIf { it != 0 }?.let {
            _processError()
        }
    }

    override fun getShiftState(): ShiftState {
        ifptr!!.setParam(Fptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_STATUS)

        ifptr!!.queryData().takeIf { it != 0 }?.let {
            _processError()
        }

        val state = ifptr!!.getParamInt(IFptr.LIBFPTR_PARAM_SHIFT_STATE)

        return when(state) {
            IFptr.LIBFPTR_SS_CLOSED.toLong() -> ShiftState.CLOSED
            IFptr.LIBFPTR_SS_OPENED.toLong() -> ShiftState.OPENED
            IFptr.LIBFPTR_SS_EXPIRED.toLong() -> ShiftState.EXPIRED
            else -> ShiftState.UNDEFINED
        }
    }

    override fun print(order: Order, operationType: OperationType) {
        setOperator()

        when(operationType) {
            OperationType.REFUND -> ifptr!!.setParam(IFptr.LIBFPTR_PARAM_RECEIPT_TYPE, IFptr.LIBFPTR_RT_SELL)
            OperationType.ORDER -> ifptr!!.setParam(IFptr.LIBFPTR_PARAM_RECEIPT_TYPE, IFptr.LIBFPTR_RT_SELL_RETURN)
            else -> _cancelAndProcessException(throw IllegalArgumentException("Unprocessed operation type $operationType"))
        }

        ifptr!!.openReceipt().takeIf { it != 0 }?.let {
            _cancelAndProcessError(it)
        }

        ifptr!!.setParam(1212, 4)
        ifptr!!.setParam(1214, 4)

        if (operationType == OperationType.REFUND) {
            ifptr!!.setParam(IFptr.LIBFPTR_PARAM_TEXT, "Возврат")
            ifptr!!.setParam(IFptr.LIBFPTR_PARAM_ALIGNMENT, IFptr.LIBFPTR_ALIGNMENT_CENTER)
            ifptr!!.printText().takeIf { it != 0 }?.let {
                _cancelAndProcessError(it)
            }
        }

        for (ticket in order.tickets) {
            registerPosition(ticket).takeIf { it != 0 }?.let { _cancelAndProcessError(it) }
        }

        val sum = order.tickets.sumOf {it.amount.toDouble()}

        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_SUM, sum)

        ifptr!!.receiptTotal()

        when (order.paymentType) {
            PaymentType.CASH -> ifptr!!.setParam(IFptr.LIBFPTR_PARAM_PAYMENT_TYPE, IFptr.LIBFPTR_PT_CASH)
            PaymentType.CARD, PaymentType.ACCOUNT_INDIVIDUAL -> ifptr!!.setParam(IFptr.LIBFPTR_PARAM_PAYMENT_TYPE, IFptr.LIBFPTR_PT_ELECTRONICALLY)
             else -> _cancelAndProcessException(throw IllegalArgumentException("Unprocessed payment type ${order.paymentType}"))
        }

        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_PAYMENT_SUM, sum)

        ifptr!!.payment().takeIf { it != 0 }?.let {
            _cancelAndProcessError(it)
        }

        ifptr!!.closeReceipt().takeIf { it != 0 }?.let { _cancelAndProcessError(it) }

        var closed = false

         closeLoop@ for (i in 1..5) {
             if (ifptr!!.checkDocumentClosed() == 0) {
                 closed = true
                 break@closeLoop
             }
            runBlocking {
                delay(500)
            }
        }

        if (!closed) {
            _cancelAndProcessException(IllegalArgumentException("Can't close document"))
        }

        if (!ifptr!!.getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_CLOSED)) {
            _cancelAndProcessException(IllegalStateException("Document isn't closed"))
        }

        if (!ifptr!!.getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_PRINTED)) {
            for(i in 1..10) {
                if (ifptr!!.continuePrint() == 0) break
                runBlocking { delay(500) }
            } // FIXME: возможно, стоит добавить какой-то cancelReceipt с проверкой
        }

        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_FN_DATA_TYPE, IFptr.LIBFPTR_FNDT_LAST_DOCUMENT)

        ifptr!!.fnQueryData().takeIf { it != 0 }?.let {
            _cancelAndProcessError(it)
        }
    }

    override fun printXReport() {
        setOperator()
        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_X)
        ifptr!!.report().takeIf { it != 0 }?.let { _processError() }
    }

    override fun printLastReceipt() {
        setOperator()
        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_LAST_DOCUMENT)
        ifptr!!.report().takeIf { it != 0 }?.let { _processError() }
    }

    override fun cashIncome(income: Float) {
        setOperator()
        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_SUM, income.toDouble())
        ifptr!!.cashIncome().takeIf { it != 0 }?.let { _processError() }
    }

    override fun setTime(zonedDateTime: ZonedDateTime) {
        val dateTime: Date = ifptr!!.getParamDateTime(IFptr.LIBFPTR_PARAM_DATE_TIME)

        println("$dateTime")

        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_DATE_TIME, Date.from(zonedDateTime.toInstant()))

        ifptr!!.writeDateTime().takeIf { it != 0 }?.let { _processError() }
    }

    private fun registerPosition(ticket: Ticket) : Int {
        val commodityName = "" +
                "${ticket.number}, " +
                "${ticket.showName} " +
                "${ticket.ageLimit}, " +
                "${ticket.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}, " +
//                "Зона: ${ticket.zone}, " +
                "Ряд: ${ticket.rowSector}, " +
                "Место: ${ticket.seatNumber}"



        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_COMMODITY_NAME, commodityName)

        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_PRICE, ticket.amount.toDouble())
        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_QUANTITY, 1)
        ifptr!!.setParam(IFptr.LIBFPTR_PARAM_TAX_TYPE, IFptr.LIBFPTR_TAX_NO)

        ifptr!!.setParam(1212, 4)
        ifptr!!.setParam(1214, 4)

        return ifptr!!.registration()
    }

    private fun _cancelAndProcessError(code: Int) {
        ifptr!!.cancelReceipt().takeIf { it != 0 }?.let {
            _processError()
        }

        _processError()
    }

    private fun _cancelAndProcessException(e: Throwable) {
        ifptr!!.cancelReceipt().takeIf { it != 0 }?.let {
            _processError()
        }

        throw e
    }

    private fun _processError() {
        throw KktException(ifptr!!.errorCode(), ifptr!!.errorDescription(), ifptr!!.errorRecommendation())
    }
}

class KktException(val code : Int, override val message: String, val recommendation: String) : Exception(message) {}
