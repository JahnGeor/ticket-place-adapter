package ru.kidesoft.ticketplace.adapter.application.usecase.action

import ru.kidesoft.ticketplace.adapter.application.port.ApiPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.StartKktSession
import ru.kidesoft.ticketplace.adapter.application.usecase.login.Login

class LoginAction(commonPort: CommonPort)
    : Usecase<Login.Input, LoginAction.Output>(commonPort) {
    class Output : Usecase.Output {}

    override suspend fun invoke(input: Login.Input?, sceneManager: SceneManager?): Output {
        Login(commonPort).invoke(inputValues = input)

        StartKktSession(commonPort).invoke()

        var output = Output()

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