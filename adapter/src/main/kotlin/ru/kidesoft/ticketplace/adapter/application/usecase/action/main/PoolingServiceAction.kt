package ru.kidesoft.ticketplace.adapter.application.usecase.action.main

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.core.PoolingServiceControl
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.GetStates
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.exception.PoolerException

class PoolingServiceAction(commonPort: CommonPort) :
    Usecase<PoolingServiceAction.Input, PoolingServiceAction.Output>(commonPort) {
    class Input
    class Output

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val states = GetStates(commonPort).invoke()

        if (!states.connectionIsOpened) throw PoolerException("Соединение с кассой отсутствует, запуск службы опроса сервера невозможен")
        if (states.shiftState != ShiftState.OPENED) throw PoolerException("Смена не открыта, запуск службы опроса сервера невозможен")

        PoolingServiceControl(commonPort).invoke(PoolingServiceControl.PoolingCommand.SMART, sceneManager = sceneManager)

        return Output()
    }

}