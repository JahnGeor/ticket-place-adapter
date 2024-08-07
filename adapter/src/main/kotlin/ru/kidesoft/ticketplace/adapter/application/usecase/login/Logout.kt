package ru.kidesoft.ticketplace.adapter.application.usecase.login

import kotlinx.coroutines.runBlocking
import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.core.PoolingServiceControl
import ru.kidesoft.ticketplace.adapter.domain.ShiftState

class Logout(commonPort: CommonPort) :
    Usecase<Logout.Input, Logout.Output>(commonPort) {

    class Input {
        val closeShift = true
    }

    class Output

    override suspend fun invoke(inputValues: Input?, sceneManager: SceneManager?): Output {
        commonPort.databasePort.execTransaction {
            val session = commonPort.databasePort.getSession().getActive()?: throw NullPointerException("Active session cannot be null")

            commonPort.kktPortFactory.getInstance()?.let {
                if (it.getConnection()) {
                    if (it.getShiftState() == ShiftState.OPENED || it.getShiftState() == ShiftState.EXPIRED) it.closeShift()
                    it.closeConnection()
                }
            }

            commonPort.databasePort.getSession().deleteById(session.id)

            runBlocking {
                PoolingServiceControl(commonPort).invoke(PoolingServiceControl.PoolingCommand.STOP)
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