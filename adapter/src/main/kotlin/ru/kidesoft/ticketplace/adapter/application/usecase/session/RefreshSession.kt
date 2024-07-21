package ru.kidesoft.ticketplace.adapter.application.usecase.session

import ru.kidesoft.ticketplace.adapter.application.port.ApiPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase

class RefreshSession(commonPort: CommonPort) : Usecase<RefreshSession.Input, RefreshSession.Output>(commonPort) {
    class Input : Usecase.Input
    class Output : Usecase.Output

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        return Output()
    }
}