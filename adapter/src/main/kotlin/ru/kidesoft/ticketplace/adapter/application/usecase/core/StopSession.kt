package ru.kidesoft.ticketplace.adapter.application.usecase.core

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.KktType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.updater.UpdateMain


class StopSession(commonPort: CommonPort) : Usecase<StopSession.Input, StopSession.Output>(commonPort) {
    class Input
    class Output

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val output = Output()

        commonPort.kktPortFactory.deleteInstance(KktType.ATOL)

        PoolingServiceControl(commonPort).invoke(PoolingServiceControl.PoolingCommand.STOP)

        UpdateMain(commonPort).invoke()

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {

    }

}