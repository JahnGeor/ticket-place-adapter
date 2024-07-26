package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.core.PoolingServiceJobName
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.ShiftState

/**
 * Описание метода: возвращает статусы ККТ. Не пробрасывает исключения на проверку кассы и web server listener. Пробрасывает исключения на обращения к базе данных.
 *
 */
class GetStates(commonPort: CommonPort): Usecase<GetStates.Input, GetStates.Output>(commonPort) {

    class Input
    class Output {
        var shiftState = ShiftState.UNDEFINED
        var connectionIsOpened: Boolean = false
        var isPoolingServiceStarted: Boolean = false
     }

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        var output = Output()

        try {
            val kktPort = GetKktPort(commonPort).invoke().kktPort
            output.shiftState = kktPort.getShiftState()
            output.connectionIsOpened = kktPort.getConnection()
            output.isPoolingServiceStarted = commonPort.jobPort.getJob(PoolingServiceJobName)?.isActive ?: false
        } catch (e: Throwable) {
            logger.error("Во время получения текущего состояния произошла ошибка: $e")
        }

        return output
    }

//    override fun present(output: Output, sceneManager: SceneManager) {
//
//    }

}