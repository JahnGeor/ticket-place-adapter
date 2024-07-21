package ru.kidesoft.ticketplace.adapter.application.usecase.updater

import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.GetStates
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import java.lang.Exception


class UpdateMain(commonPort: CommonPort) :
    Usecase<UpdateMain.Input, UpdateMain.Output>(commonPort) {

        class Input : Usecase.Input {}
    class Output() : Usecase.Output {
        var profile: Profile? = null
        lateinit var status: GetStates.Output
    }


    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        var output = Output()

        output.profile = commonPort.databasePort.getProfile().getByCurrent()
            ?: throw NullPointerException("Current user profile is null")

        try {
            var states = GetStates(commonPort).invoke()
            output.status = states
        } catch (e: Exception) {
            logger.error("Во время получения статусов главного окна произошла ошибка: $e")
        }

        sceneManager?.let {
            present(output, sceneManager)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        val presenter = sceneManager.getPresenter(MainPresenter::class)
            ?: throw NullPointerException("${MainPresenter::class} not found")

        output.profile?.let {
            presenter.setProfile(it)
        }

        output.status.shiftState?.let {
            presenter.setShiftState(it)
        }

        output.status.connectionIsOpened?.let {
            presenter.setKktConnectionStatus(it)
        }

        output.status.isPoolingServiceStarted?.let {
            presenter.setPoolingStatus(it)
        }

    }


}