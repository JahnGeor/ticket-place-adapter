package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.domain.ShiftState

class StartSessionUsecase(val database : DatabasePort, val kktPortFactory: KktPortFactory) : _Usecase<StartSessionUsecase.Input, StartSessionUsecase.Output>() {
    class Input : _Usecase.Input
    class Output : _Usecase.Output {

    }

    override suspend fun execute(input: Input?): Output {

        val profile = database.getProfile().getCurrentProfile()?: throw IllegalArgumentException("Profile can't be null")

        val setting = database.getSetting().getCurrentSetting() ?: throw IllegalArgumentException("Setting can't be null")


        val kktInstance = kktPortFactory.getInstance(kktType = KktType.ATOL, profile.loginId) ?: kktPortFactory.createInstance(KktType.ATOL, setting.kkt, profile.loginId)


        if (!kktInstance.getConnection()) kktInstance.openConnection()

        when(kktInstance.getShiftState()) {
            ShiftState.CLOSED -> kktInstance.openShift(profile.cashier)
            ShiftState.EXPIRED -> {
                kktInstance.closeShift(profile.cashier)
                kktInstance.openShift(profile.cashier)
            }
            else -> {}
        }

        return Output()
    }

    override fun present(output: Output, sceneManager: SceneManager) {

    }
}