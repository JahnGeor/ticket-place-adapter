package ru.kidesoft.ticketplace.adapter.application.port

import kotlinx.coroutines.Job
import ru.kidesoft.ticketplace.adapter.domain.click.Click
import ru.kidesoft.ticketplace.adapter.domain.click.ClickInfo
import ru.kidesoft.ticketplace.adapter.domain.history.History
import ru.kidesoft.ticketplace.adapter.domain.history.HistoryInfo
import ru.kidesoft.ticketplace.adapter.domain.history.Step
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import ru.kidesoft.ticketplace.adapter.domain.login.LoginInfo
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.profile.ProfileInfo
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.domain.session.SessionInfo
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting
import ru.kidesoft.ticketplace.adapter.domain.setting.SettingInfo
import java.time.ZonedDateTime
import java.util.UUID

interface DatabasePort {
    fun getLogin() : LoginPort
    fun getSession() : SessionPort
    fun getClick() : ClickPort
    fun getProfile() : ProfilePort
    fun getSetting() : SettingPort
    fun getHistory() : HistoryPort

    fun execTransaction(transaction: () -> Boolean)
}

interface LoginPort {
    fun getLoginId(email: String, url: String): UUID?
    fun save(login: LoginInfo) : UUID
    fun getAll(): List<Login>
    fun getByCurrent(): Login?
    fun deleteAll() : Int
    fun deleteById(vararg id: UUID) : Int
}

interface SessionPort {
    fun saveByCurrent(sessionInfo: SessionInfo, active: Boolean) : UUID
    fun saveByLoginId(loginId: UUID, sessionInfo: SessionInfo, active: Boolean = true): UUID
    fun deactivateExcept(loginId: UUID) : Int
    fun getActive() : Session?
    fun getByLoginId(loginId: UUID): Session?
    fun setActive(sessionId: UUID) : Int
    fun setDeactive() : Int
    fun deleteById(id: UUID) : Int
    fun deleteAll() : Int
    fun deleteActive() : Int
}

interface ClickPort {
    fun saveByCurrent(clickInfo: ClickInfo): UUID
    fun getByCurrent(): Click?
}

interface ProfilePort {
    fun getCashierList() : List<Cashier>
    fun saveByLoginId(loginId: UUID, profileInfo: ProfileInfo) : UUID
    fun getByLoginId(loginId : UUID) : Profile?
    fun getByCurrent(): Profile?
    fun getCurrentCashier() : Cashier?
    fun getCashierByLoginId(loginId: UUID) : Cashier?
}

interface SettingPort {
    fun saveByLoginId(loginId: UUID, setting : SettingInfo) : UUID
    fun saveByCurrent(setting: SettingInfo): UUID
    fun getByCurrent(): Setting?
    fun setDefault(loginId: UUID): UUID
    fun getById(id: UUID): Setting?
    fun getByLoginId(loginId: UUID): Setting?
}


interface HistoryPort {
    fun saveByCurrent(orderId: Int, step: Step, historyInfo: HistoryInfo): UUID?
    fun getListByCurrent(from: ZonedDateTime? = null): List<History>
}

interface PropertiesPort {
    fun getVersion() : String
}

interface JobPort {
    fun saveJob(key: String, job: Job)
    fun getJob(key: String): Job?
    fun deleteJob(key: String)
}