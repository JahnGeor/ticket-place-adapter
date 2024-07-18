package ru.kidesoft.ticketplace.adapter.application.port

import net.datafaker.providers.base.Bool
import ru.kidesoft.ticketplace.adapter.domain.history.History
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.profile.ProfileExposed
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.domain.session.SessionExposed
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting
import ru.kidesoft.ticketplace.adapter.domain.setting.SettingExposed
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
    fun GetLoginId(email: String, url: String): UUID?
    fun Create(login: LoginExposed): Login
    fun Update(id: UUID, login: LoginExposed): Login
    fun GetAll(): List<Login>
    fun getCurrent(): Login?
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

}

interface ProfilePort {
    fun getCashierList() : List<Cashier>
    fun Create(profile: ProfileExposed): Profile
    fun Update(id: UUID, profile: ProfileExposed): Profile
    fun getByLoginId(loginId : UUID) : Profile?
    fun getCurrentProfile(): Profile?
    fun getCurrentCashier() : Cashier?
}

interface SettingPort {
    fun getByCurrentUser(): Setting?
    fun create(setting: SettingExposed): Setting
    fun update(id: UUID, setting: SettingExposed): Setting
    fun getById(id: UUID): Setting?
    fun getByLoginId(loginId: UUID): Setting?
    fun createDefault(loginId: UUID): Setting?
}

interface HistoryPort {
    fun getListByCurrentUser(): List<History>
}

interface PropertiesPort {
    fun getVersion() : String
}