package ru.kidesoft.ticketplace.adapter.application.usecase.login

import ru.kidesoft.ticketplace.adapter.application.port.ApiFactory
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase

class ReactivateAuthorization(val databasePort: DatabasePort, val apiFactory: ApiFactory) : _Usecase<ReactivateAuthorization.Input, ReactivateAuthorization.Output>() {
    class Input : _Usecase.Input {}
    class Output : _Usecase.Output {}

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val currentLogin = databasePort.getLogin().getCurrent()?: throw NullPointerException("current login is null")

        Login(databasePort, apiFactory).invoke(Login.Input().apply {
            url = currentLogin.url
            password = currentLogin.password
            email = currentLogin.email
        })

        val output = Output()

        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.showNotification(NotificationType.INFO, "Обновлена текущая сессия", "Реавторизация")
    }
}