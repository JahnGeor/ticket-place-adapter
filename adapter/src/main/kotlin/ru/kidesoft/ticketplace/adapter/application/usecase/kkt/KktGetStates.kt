package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.PoolingServiceJobName
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.memory.JobRepository

/**
 * Описание метода: возвращает статусы ККТ. Не пробрасывает исключения на проверку кассы и web server listener. Пробрасывает исключения на обращения к базе данных.
 *
 */
class GetStates(commonPort: CommonPort): Usecase<GetStates.Input, GetStates.Output>(commonPort) {

    class Input : Usecase.Input
    class Output : Usecase.Output {
        var shiftState = ShiftState.UNDEFINED
        var connectionIsOpened: Boolean = false
        var isPoolingServiceStarted: Boolean = false
     }

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        var output = Output()

        try {
            val kktPort = GetCurrentKktPort(commonPort).invoke().kktPort
            output.shiftState = kktPort.getShiftState()
            output.connectionIsOpened = kktPort.getConnection()

            output.isPoolingServiceStarted = commonPort.jobPort.getJob(PoolingServiceJobName)?.let {
                it.isActive
            }?: false
        } catch (e: Throwable) {
            logger.error("Во время получения текущего состояния произошла ошибка: $e")
        }

        return output
    }

//    override fun present(output: Output, sceneManager: SceneManager) {
//
//    }

}