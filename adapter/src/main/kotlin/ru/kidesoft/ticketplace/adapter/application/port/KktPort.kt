package ru.kidesoft.ticketplace.adapter.application.port

import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.setting.KktSetting
import java.util.UUID

enum class KktType {
    ATOL
}

interface KktPort : KktConnection, KktShift {

}

interface KktConnection {
    fun setConnection(kktSetting: KktSetting? = null)
    fun getConnection() : Boolean
    fun openConnection()
    fun closeConnection()
}

interface KktShift {
    fun openShift(cashier: Cashier)
    fun closeShift(cashier: Cashier)
    fun getShiftState() : ShiftState
}

interface KktOperation {
    fun printXReport(cashier: Cashier)
    fun printLastReceipt(cashier: Cashier)
    fun cashIncome(income: Float, cashier: Cashier)
}

interface KktPrinter {
    fun print(cashier: Cashier)
}

interface KktPortFactory {
    fun getInstance(kktType: KktType, loginId : UUID) : KktPort?
    fun createInstance(kktType: KktType, kktSetting: KktSetting, loginId: UUID) : KktPort
}
