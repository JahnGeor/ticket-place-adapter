package ru.kidesoft.ticketplace.adapter.application.port

import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.order.OperationType
import ru.kidesoft.ticketplace.adapter.domain.order.Order
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.setting.KktSetting
import java.time.ZonedDateTime

enum class KktType {
    ATOL
}

interface KktPort : KktConnection, KktShift, KktPrinter, KktOperation, KktSystem {
    fun destroy()
}

interface KktSystem {
    fun setTime(zonedDateTime: ZonedDateTime)
}

interface KktConnection {
    fun setConnection(kktSetting: KktSetting? = null)
    fun getConnection() : Boolean
    fun openConnection()
    fun closeConnection()
}

interface KktShift {
    fun openShift()
    fun closeShift()
    fun getShiftState() : ShiftState
}

interface KktOperation {
    fun printXReport()
    fun printLastReceipt()
    fun cashIncome(income: Float)
}

interface KktPrinter {
    fun print(order: Order, operationType: OperationType)
}

interface KktPortFactory {
    fun getInstance() : KktPort?
    fun createInstance(kktType: KktType, cashierData : Cashier, kktSetting: KktSetting? = null): KktPort
    fun deleteInstance(kktType: KktType)
}
