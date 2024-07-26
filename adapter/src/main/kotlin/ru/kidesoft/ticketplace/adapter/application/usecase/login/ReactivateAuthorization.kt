package ru.kidesoft.ticketplace.adapter.application.usecase.login

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting

class ReactivateAuthorization(commonPort: CommonPort) : Usecase<ReactivateAuthorization.Input, Login.Output>(commonPort) {
    class Input

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Login.Output {
        val currentLogin = commonPort.databasePort.getLogin().getByCurrent()?: throw NullPointerException("current login is null")

        val loginOutput = Login(commonPort).invoke(Login.Input(currentLogin.email, currentLogin.password, currentLogin.url))

        logger.info("Текущая сессия переактивирована: ${loginOutput.profile.userId} ${loginOutput.session.token.createdTime} -> ${loginOutput.session.token.expiredTime}")

        sceneManager?.let {
            present(loginOutput, it)
        }

        return loginOutput
    }

    override fun present(output: Login.Output, sceneManager: SceneManager) {
        sceneManager.showNotification(NotificationType.INFO, "Реавторизация", "Обновлена текущая сессия")
    }
}