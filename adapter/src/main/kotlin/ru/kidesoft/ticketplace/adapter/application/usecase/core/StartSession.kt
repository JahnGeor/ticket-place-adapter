package ru.kidesoft.ticketplace.adapter.application.usecase.core

import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import java.time.ZonedDateTime

/**
 * Вариант использования запуска сеанса работы кассира. Пробрасывает исключения. Включает в себя старт работы с кассой ККТ, а также запускает службу опроса удаленного сервера.
 * @author Егор Ишуткин
 * @property commonPort Порт, сочетающий в себе работу с данными, работу с кассой ККТ, а также работу с физическим принтером
 * @exception KktException
 */
class StartSession(commonPort: CommonPort) : Usecase<StartSession.Input, StartSession.Output>(commonPort) {
    class Input
    class Output

    override suspend fun invoke(inputValues: Input?, sceneManager: SceneManager?): Output {

        val cashier = commonPort.databasePort.getProfile().getCurrentCashier()?: throw NullPointerException("Profile can't be null")

        val setting = commonPort.databasePort.getSetting().getByCurrent() ?: throw NullPointerException("Setting can't be null")

        val kktInstance = commonPort.kktPortFactory.createInstance(KktType.ATOL, cashier, setting.kktSetting)

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