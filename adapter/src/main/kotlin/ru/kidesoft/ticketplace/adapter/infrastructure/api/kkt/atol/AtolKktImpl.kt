package ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt.atol

import ru.atol.drivers10.fptr.Fptr
import ru.atol.drivers10.fptr.IFptr
import ru.kidesoft.ticketplace.adapter.application.port.KktPort
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.setting.KktSetting
import java.io.File

class AtolKktImpl : KktPort {

    private lateinit var ifptr: IFptr

    constructor() {
        ifptr = Fptr()
        ifptr.setSingleSetting(IFptr.LIBFPTR_SETTING_AUTO_RECONNECT, "false")
        ifptr.applySingleSettings()
    }

    constructor(kktSetting: KktSetting) {
        setConnection(kktSetting)
    }


    override fun setConnection(kktSetting: KktSetting?) {
        var driverDir = kktSetting?.let {
            val driverDir = File(kktSetting.path)

            if (!driverDir.exists() || !driverDir.isDirectory) {
                throw IllegalArgumentException("driver dir does not exist or isn't a directory")
            }

            driverDir
        }?: null

        ifptr?.let { it.destroy() }

        ifptr = driverDir?.let { Fptr(it.canonicalPath) } ?: Fptr()

        kktSetting?.let {
            ifptr.setSingleSetting(IFptr.LIBFPTR_SETTING_AUTO_RECONNECT, it.autoRecconect.toString())
            ifptr.applySingleSettings()
        }
    }

    override fun getConnection(): Boolean {
        ifptr.setParam(IFptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_STATUS)

        ifptr.queryData().takeIf { it != 0 }?.let {
            return@getConnection false
        }

        return ifptr.isOpened
    }

    override fun openConnection() {
        ifptr.open().takeIf { it != 0 }?.let {
            _processError(it)
        }
    }

    override fun closeConnection() {
        ifptr.close().takeIf { it != 0 }?.let {
            _processError(it)
        }
    }

    override fun openShift(cashier: Cashier) {
        ifptr.openShift().takeIf { it != 0 }?.let {
            _processError(it)
        }
    }

    override fun closeShift(cashier: Cashier) {
        ifptr.setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_CLOSE_SHIFT)
        ifptr.report().takeIf { it != 0 }?.let {
            _processError(it)
        }
        ifptr.checkDocumentClosed().takeIf { it != 0 }?.let {
            _processError(it)
        }
    }

    override fun getShiftState(): ShiftState {
        ifptr.setParam(Fptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_STATUS)

        ifptr.queryData().takeIf { it != 0 }?.let {
            _processError(it)
        }

        val state = ifptr.getParamInt(IFptr.LIBFPTR_PARAM_SHIFT_STATE)

        return when(state) {
            IFptr.LIBFPTR_SS_CLOSED.toLong() -> ShiftState.CLOSED
            IFptr.LIBFPTR_SS_OPENED.toLong() -> ShiftState.OPENED
            IFptr.LIBFPTR_SS_EXPIRED.toLong() -> ShiftState.EXPIRED
            else -> ShiftState.UNDEFINED
        }
    }

    private fun _cancelAndProcessError(code: Int) {
        ifptr.cancelReceipt().takeIf { it != 0 }?.let {
            _processError(it)
        }

        _processError(code)
    }

    private fun _processError(code: Int) {
        throw KktException(code, ifptr.errorDescription(), ifptr.errorRecommendation())
    }
}

class KktException(val code : Int, override val message: String, val recommendation: String) : Exception(message) {}
