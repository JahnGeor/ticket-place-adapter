package ru.kidesoft.ticketplace.adapter.application.usecase.login

import ru.kidesoft.ticketplace.adapter.application.dto.LoginData

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.*
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.exception.AuthorizationException
import ru.kidesoft.ticketplace.adapter.domain.exception.ValidationException

import ru.kidesoft.ticketplace.adapter.domain.login.LoginInfo
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.profile.ProfileInfo
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.domain.session.SessionInfo
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting
import ru.kidesoft.ticketplace.adapter.domain.setting.SettingInfo

/**
 * @exception ru.kidesoft.ticketplace.adapter.domain.exception.AuthorizationException
 */
class Login(commonPort: CommonPort) :
    Usecase<Login.Input, Login.Output>(commonPort) {

    class Input(var email: String,
                var password: String,
                var url: String)


    class Output(var session: Session, var profile: Profile, var setting: Setting) {

    }

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        return kotlin.runCatching {
            validate(input)
            val loginInfo = LoginInfo(url = input!!.url, email = input.email, password = input.password)

            val loginData = authorization(loginInfo)

            val output = save(loginInfo, loginData.mapToSession(), loginData.mapToProfile())

            output
        }.onFailure {
            throw AuthorizationException("Во время попытки авторизации произошла ошибка", it)
        }.getOrThrow()
    }

    private suspend fun authorization(loginInfo: LoginInfo) : LoginData {
        return commonPort.apiPortFactory.getInstance(loginInfo.url).login(loginInfo.email, loginInfo.password)
    }

    // Transaction func
    private fun save(loginInfo: LoginInfo, sessionInfo: SessionInfo, profileInfo: ProfileInfo) : Output {

        var output : Output? = null

        commonPort.databasePort.execTransaction {
            val loginId = commonPort.databasePort.getLogin().save(loginInfo)
            val profileId = commonPort.databasePort.getProfile().saveByLoginId(loginId, profileInfo)
            val sessionId = commonPort.databasePort.getSession().saveByLoginId(loginId, sessionInfo)

            val setting = commonPort.databasePort.getSetting().getByLoginId(loginId) ?: run {
                val id = commonPort.databasePort.getSetting().setDefault(loginId)
                Setting(id, loginId, SettingInfo.getDefault())
            }

            commonPort.databasePort.getSession().setActive(sessionId)
            val session = Session(sessionId, loginId, sessionInfo)
            val profile = Profile(profileId,loginId, profileInfo)


            output = Output(session, profile, setting)

            true
        }


        return output?: throw NullPointerException("Output of Login Usecase is null")
    }

    private fun validate(inputValues: Input?) {
        if (inputValues == null) {
            throw ValidationException()
        }
    }


}