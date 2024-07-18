package ru.kidesoft.ticketplace.adapter.application.usecase.login

import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.KktType
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase

class Logout(private val databasePort: DatabasePort, val kktPortFactory: KktPortFactory) :
    _Usecase<Logout.Input, Logout.Output>() {
    private val logger = LogManager.getLogger(Logout::class.java)

    class Input : _Usecase.Input {
        val closeShift = true
    }

    class Output : _Usecase.Output {}

    override suspend fun invoke(inputValues: Input?, sceneManager: SceneManager?): Output {
        databasePort.execTransaction {
            val session = databasePort.getSession().getActive()?: throw NullPointerException("Active session cannot be null")

            kktPortFactory.getInstance(KktType.ATOL, session.loginId)?.let {
                if (it.getConnection()) {
                    val profile = databasePort.getProfile().getCurrentProfile() ?: throw NullPointerException("Profile can not be null")
                    it.closeShift(profile.cashier)
                    it.closeConnection()
                }
            }

            databasePort.getSession().deleteById(session.id)

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