package ru.kidesoft.ticketplace.adapter.application.usecase.session

import ru.kidesoft.ticketplace.adapter.application.port.ApiPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase

class IsActiveSessionUsecase(commonPort: CommonPort) : Usecase<IsActiveSessionUsecase.Input, IsActiveSessionUsecase.Output>(commonPort) {

    class Input : Usecase.Input {}

    class Output(val isActive : Boolean) : Usecase.Output {}

    override suspend fun invoke(inputValues: Input?, sceneManager: SceneManager?): Output {
        return Output(isActive = commonPort.databasePort.getSession().getActive() != null )
    }

}