package ru.kidesoft.ticketplace.adapter.application.port

import kotlinx.coroutines.Job
import ru.kidesoft.ticketplace.adapter.domain.click.ClickDatabase
import ru.kidesoft.ticketplace.adapter.domain.click.ClickExposed
import ru.kidesoft.ticketplace.adapter.domain.history.History
import ru.kidesoft.ticketplace.adapter.domain.history.HistoryPayload
import ru.kidesoft.ticketplace.adapter.domain.history.Step
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.profile.ProfileExposed
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.domain.session.SessionExposed
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting
import ru.kidesoft.ticketplace.adapter.domain.setting.SettingExposed
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
    fun create(login: LoginExposed): Login
    fun update(id: UUID, login: LoginExposed): Login
    fun getAll(): List<Login>
    fun getByCurrent(): Login?
}

interface SessionPort {
    fun getActive() : Session?
    fun create(session: SessionExposed) : Session
    fun update(sessionId: UUID, sessionExposed: SessionExposed) : Session
    fun getByLoginId(loginId: UUID): Session?
    fun setActive(sessionId: UUID)
    fun setDeactive()
    fun deleteById(id: UUID)
    fun deleteAll()
    fun deleteActive()
}

interface ClickPort {
//    fun saveByCurrent(clickExposed: ClickExposed): ClickDatabase
//
//    fun getLastByCurrent(): ClickDatabase?

    fun save(clickExposed: ClickExposed): UUID
    fun getByCurrent(): ClickDatabase?
}

interface ProfilePort {
    fun getCashierList() : List<Cashier>
    fun create(profile: ProfileExposed): Profile
    fun update(id: UUID, profile: ProfileExposed): Profile
    fun getByLoginId(loginId : UUID) : Profile?
    fun getByCurrent(): Profile?
    fun getCurrentCashier() : Cashier?
}

interface SettingPort {
    fun getByCurrent(): Setting?
    fun create(setting: SettingExposed): Setting
    fun update(id: UUID, setting: SettingExposed): Setting
    fun getById(id: UUID): Setting?
    fun getByLoginId(loginId: UUID): Setting?
    fun createDefault(loginId: UUID): Setting
}

interface HistoryPort {
    fun save(orderId: Int, step: Step, historyPayload: HistoryPayload): History
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