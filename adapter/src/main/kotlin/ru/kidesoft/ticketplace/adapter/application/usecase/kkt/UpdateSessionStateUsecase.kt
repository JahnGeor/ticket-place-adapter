package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.KktType
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.domain.ShiftState

class UpdateSessionStateUsecase(val database: DatabasePort, val kktPortFactory: KktPortFactory) : _Usecase<UpdateSessionStateUsecase.Input, UpdateSessionStateUsecase.Output>() {
    private val logger = LogManager.getLogger()
    class Input : _Usecase.Input {}

    class Output : _Usecase.Output {
        var isConnected : Boolean = false
        var shiftState : ShiftState = ShiftState.UNDEFINED
    }


    override suspend fun execute(input: Input?, sceneManager: SceneManager?): Output {
        val profile = database.getProfile().getCurrentProfile() ?: throw NullPointerException("profile is null")
        val setting = database.getSetting().getByCurrentUser() ?: throw NullPointerException("setting is null")

        val kktPort = kktPortFactory.getInstance(KktType.ATOL, profile.loginId)?: kktPortFactory.createInstance(
            KktType.ATOL, setting.kkt,
            profile.loginId
        )

        val output = Output()

        try {
            output.isConnected = kktPort.getConnection()
        } catch (e : Exception) {
            logger.warn("В процессе запроса данных по состоянию кассы произошла ошибка: $e")
            output.isConnected = false
        }

        try {
            output.shiftState = kktPort.getShiftState()
        } catch (e : Exception) {
            logger.warn("В процессе запроса данных по состоянию смены произошла ошибка: $e")
            output.shiftState = ShiftState.UNDEFINED
        }

        if (!output.isConnected || output.shiftState == ShiftState.UNDEFINED) {
            sceneManager?.showNotification(NotificationType.ERROR, "Во время проверки состояния кассы произошла ошибка", "Неизвестное состояние соединения и/или смены ККТ")
        }

        sceneManager?.getPresenter(MainPresenter::class)?.setKktState(output.isConnected, output.shiftState) ?: throw NullPointerException("${MainPresenter::class::simpleName} is not found")

        return output
    }

}