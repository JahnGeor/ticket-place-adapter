package ru.kidesoft.ticketplace.adapter.application.usecase.login

import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.PoolingService
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase

class Logout(commonPort: CommonPort) :
    Usecase<Logout.Input, Logout.Output>(commonPort) {

    class Input {
        val closeShift = true
    }

    class Output

    override suspend fun invoke(inputValues: Input?, sceneManager: SceneManager?): Output {
        commonPort.databasePort.execTransaction {
            val session = commonPort.databasePort.getSession().getActive()?: throw NullPointerException("Active session cannot be null")

            commonPort.kktPortFactory.getInstance(KktType.ATOL, session.loginId)?.let {
                if (it.getConnection()) {
                    val profile = commonPort.databasePort.getProfile().getByCurrent() ?: throw NullPointerException("Profile can not be null")
                    it.closeShift()
                    it.closeConnection()
                }
            }

            commonPort.databasePort.getSession().deleteById(session.id)

            runBlocking {
                PoolingService(commonPort).invoke(PoolingService.Input(forceState = false))
            }

            return@execTransaction true
        }



        // TODO: Listener delete

        val output = Output()

        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.openScene(Scene.AUTH)
    }
}