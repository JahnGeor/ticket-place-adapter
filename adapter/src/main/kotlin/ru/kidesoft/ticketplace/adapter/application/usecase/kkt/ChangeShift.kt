package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import ru.kidesoft.ticketplace.adapter.application.port.ApiPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt.KktFactory
import ru.kidesoft.ticketplace.adapter.ui.view.MainViewController

class ChangeShift(commonPort: CommonPort) :
    Usecase<ChangeShift.Input, ChangeShift.Output>(commonPort) {
    class Input()
    class Output(val shiftState: ShiftState)

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val kktPort = GetCurrentKktPort(commonPort).invoke().kktPort

        val cashier = commonPort.databasePort.getProfile().getCurrentCashier()
            ?: throw IllegalArgumentException("Не найдены данные профиля по текущему пользователю")

        when (val shiftState = kktPort.getShiftState()) {
            ShiftState.OPENED, ShiftState.EXPIRED -> kktPort.closeShift()
            ShiftState.CLOSED -> kktPort.openShift()
            else -> throw IllegalArgumentException("Неизвестное состояние смены: $shiftState")
        }

        val output = Output(kktPort.getShiftState())

        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.showNotification(NotificationType.INFO, "Операция с кассой", "Операция по изменению состояния смены успешно завершена.")
        sceneManager.getPresenter(MainViewController::class)?.setShiftState(output.shiftState)
    }

}