package ru.kidesoft.ticketplace.adapter.application.usecase.web

import ru.kidesoft.ticketplace.adapter.application.port.ApiPort
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.login.Login
import ru.kidesoft.ticketplace.adapter.application.usecase.login.ReactivateAuthorization
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import java.time.ZonedDateTime

class GetWebPort(commonPort: CommonPort): Usecase<GetWebPort.Input, GetWebPort.Output>(commonPort) {
    class Input {
        val reactivate: Boolean = true
    }

    class Output(var webPort : ApiPort)

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val reactivate = input?.reactivate ?: false

        val activeSession : Session? = commonPort.databasePort.getSession().getActive()?.let {
            if (!it.isExpired()) it else {
                if (reactivate) ReactivateAuthorization(commonPort).invoke(sceneManager = sceneManager).session else it
            }
        } ?: let {
            if (reactivate) ReactivateAuthorization(commonPort).invoke().session else null
        }

        val activeLogin = commonPort.databasePort.getLogin().getByCurrent()
            ?: throw IllegalArgumentException("Не найдены данные авторизации по текущему пользователю")

        val currentSetting = commonPort.databasePort.getSetting().getByCurrent()
            ?: throw IllegalArgumentException("Не найдены данные настроек по текущему пользователю")

        val profile = commonPort.databasePort.getProfile().getByCurrent()?: throw IllegalArgumentException("Не найдены данные профиля по текущему пользователю")

        val apiPort = commonPort.apiPortFactory.getInstance(
            activeLogin.url,
            token = activeSession?.token?.value,
            tokenType = activeSession?.token?.type,
            timeout = currentSetting.server.requestTimeout,
            userId = profile.userId.toInt()
        )

        val output = Output(apiPort)

        return output
    }
}