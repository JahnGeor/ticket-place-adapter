package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.mock

import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.profile.ProfileExposed
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.domain.session.SessionExposed
import java.util.*

class DatabaseMock : DatabasePort{
    override fun getLogin(): LoginPort = ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.mock.Login()

    override fun getSession(): SessionPort = ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.mock.Session()

    override fun getClick(): ClickPort = Click()

    override fun getProfile(): ProfilePort = ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.mock.Profile()

    override fun getSetting(): SettingPort {
        TODO("Not yet implemented")
    }

    override fun getHistory(): HistoryPort {
        TODO("Not yet implemented")
    }
}

class Login : LoginPort {
    override fun GetLoginId(email: String, url: String): UUID = UUID.randomUUID()

    override fun Create(login: LoginExposed): Login = Login()

    override fun Update(id: UUID, login: LoginExposed): Login = Login()

    override fun GetAll(): List<Login> = listOf(Login())

}

class Session : SessionPort {
    override fun getActive(): Session? = Session()

    override fun create(session: SessionExposed): Session = Session()

    override fun update(sessionId: UUID, sessionExposed: SessionExposed): Session = Session()

    override fun getByLoginId(loginId: UUID): Session? = Session()

    override fun setActive(sessionId: UUID) {}

    override fun setDeactive() {}

    override fun deleteById(id: UUID) {

    }

    override fun deleteAll() {
    }

    override fun deleteActive() {
    }

}

class Click : ClickPort {

}

class Profile : ProfilePort {
    override fun getCashierList(): List<Cashier> = listOf(Cashier().apply {
        fullName = "Администратор"
        inn = 12312341234
    })

    override fun Create(profile: ProfileExposed): Profile = Profile()

    override fun Update(id: UUID, profile: ProfileExposed): Profile = Profile()

    override fun getByLoginId(loginId: UUID): Profile? = Profile()

    override fun getCurrentProfile(): Profile = Profile().apply { cashier =
        Cashier().apply {
            fullName = "Администратор"
            inn = 12312341234 } }

    override fun getCurrentCashier(): Cashier? {
        TODO("Not yet implemented")
    }
}