package ru.kidesoft.ticketplace.adapter.application.usecase.action.auth

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.AlertType
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.core.PoolingServiceControl
import ru.kidesoft.ticketplace.adapter.application.usecase.core.StartSession
import ru.kidesoft.ticketplace.adapter.application.usecase.login.Login

class LoginAction(commonPort: CommonPort)
    : Usecase<Login.Input, LoginAction.Output>(commonPort) {
    class Output

    override suspend fun invoke(input: Login.Input?, sceneManager: SceneManager?): Output {

        Login(commonPort).invoke(input = input)

        kotlin.runCatching {
            StartSession(commonPort).invoke()
            PoolingServiceControl(commonPort).invoke(PoolingServiceControl.PoolingCommand.START)
        }.onFailure {
            logger.error("Ошибка в процессе запуска сеанса работы кассира: ${it.message}")
            sceneManager?.showAlert(AlertType.ERROR, "Ошибка начала сеанса", "Во время активации сессии ККТ и/или службы опроса сервера произошла ошибка", it)
        }


        val output = Output()

        sceneManager?.let {
            present(output, it)
        }

        // TODO: StartListener

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.openScene(Scene.MAIN)
    }
}