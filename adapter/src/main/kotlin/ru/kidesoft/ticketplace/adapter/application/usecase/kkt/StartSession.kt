package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import java.time.ZonedDateTime
import kotlin.math.log

class StartKktSession(commonPort: CommonPort) : Usecase<StartKktSession.Input, StartKktSession.Output>(commonPort) {
    class Input
    class Output

    override suspend fun invoke(inputValues: Input?, sceneManager: SceneManager?): Output {

        val profile = commonPort.databasePort.getProfile().getByCurrent()?: throw NullPointerException("Profile can't be null")

        val setting = commonPort.databasePort.getSetting().getByCurrent() ?: throw NullPointerException("Setting can't be null")

        val kktInstance = commonPort.kktPortFactory.createInstance(KktType.ATOL, profile.cashier, setting.kkt, profile.loginId)

        if (!kktInstance.getConnection()) kktInstance.openConnection()

        val currentTime = ZonedDateTime.now()

        when(kktInstance.getShiftState()) {
            ShiftState.CLOSED -> {
                kktInstance.setTime(currentTime)
                logger.info("Время ККТ установлено на $currentTime")
                kktInstance.openShift()
            }
            ShiftState.EXPIRED -> {
                kktInstance.closeShift()
                kktInstance.setTime(ZonedDateTime.now())
                logger.info("Время ККТ установлено на $currentTime")
                kktInstance.openShift()
            }
            else  -> {
                logger.warn("Невозможно установить текущее время, смена открыта")
            } // Если открытая - ничего не делаем // TODO: Потом необходимо осуществлять проверку
        }

        return Output()
    }

}