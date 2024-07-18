package ru.kidesoft.ticketplace.adapter.application.usecase.updater

import ru.kidesoft.ticketplace.adapter.Main
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktConnection
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.KktType
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile


class UpdateMain(val databasePort: DatabasePort, val kktPortFactory: KktPortFactory) :
    _Usecase<UpdateMain.Input, UpdateMain.Output>() {
    class Input : _Usecase.Input {}
    class Output(val profile: Profile, val kktConnection: Boolean, val shiftState: ShiftState) : _Usecase.Output {}


    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val profile =
            databasePort.getProfile().getCurrentProfile() ?: throw NullPointerException("Current user profile is null")
        val setting = databasePort.getSetting().getByLoginId(profile.loginId)
            ?: throw NullPointerException("Current user setting is null")
        val kktPort = kktPortFactory.getInstance(KktType.ATOL, profile.loginId) ?: kktPortFactory.createInstance(
            KktType.ATOL,
            setting.kkt,
            setting.loginId
        )

        val shiftState = kktPort.getShiftState()
        val kktConnection = kktPort.getConnection()
        // TODO: listenerStatus = listener.getStatus()

        val output = Output(profile, kktConnection, shiftState)

        sceneManager?.let {
            present(output, sceneManager)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        val presenter = sceneManager.getPresenter(MainPresenter::class)
            ?: throw NullPointerException("${MainPresenter::class} not found")
        presenter.setProfile(output.profile)
        presenter.setKktState(output.kktConnection, output.shiftState)
    }

}