package ru.kidesoft.ticketplace.adapter.application.usecase.action

import ru.kidesoft.ticketplace.adapter.application.port.ApiFactory
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.StartKktSession
import ru.kidesoft.ticketplace.adapter.application.usecase.login.Login

class LoginAction(private val databasePort: DatabasePort, private val apiFactory: ApiFactory, private val kktPortFactory: KktPortFactory) : _Usecase<Login.Input, LoginAction.Output>() {
    class Output : _Usecase.Output {}

    override suspend fun invoke(input: Login.Input?, sceneManager: SceneManager?): Output {
        Login(databasePort = databasePort, apiFactory = apiFactory).invoke(inputValues = input)
        StartKktSession(databasePort = databasePort, kktPortFactory = kktPortFactory).invoke()

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