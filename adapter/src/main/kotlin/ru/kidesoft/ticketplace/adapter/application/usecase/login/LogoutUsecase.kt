package ru.kidesoft.ticketplace.adapter.application.usecase.login

import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.ApiFactory
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed

class LogoutUsecase(private val databasePort: DatabasePort) :
    _Usecase<LogoutUsecase.Input, LogoutUsecase.Output, Presenter>() {
    private val logger = LogManager.getLogger(LogoutUsecase::class.java)

    class Input : _Usecase.Input {}

    class Output : _Usecase.Output {}

    override suspend fun execute(inputValues: Input): Output {
        // databasePort.getSession().setDeactive()
        databasePort.getSession().deleteActive()
        return LogoutUsecase.Output()
    }

    override fun present(output: Output, presenter: Presenter) {
        presenter.getSceneManager().openScene(Scene.AUTH)
    }
}