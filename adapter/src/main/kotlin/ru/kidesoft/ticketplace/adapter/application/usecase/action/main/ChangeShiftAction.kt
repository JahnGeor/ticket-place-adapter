package ru.kidesoft.ticketplace.adapter.application.usecase.action.main

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.KktType
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.core.PoolingServiceControl
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.GetKktPort
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.ui.view.MainViewController

class ChangeShiftAction(commonPort: CommonPort) :
    Usecase<ChangeShiftAction.Input, ShiftState>(commonPort) {

    class Input(
        val cashier: Cashier? = null,
        val forceState: ShiftAction = ShiftAction.SMART,
        val deletePortAfterUse: Boolean = false,
        val isPoolerControl: Boolean = false,
    )

    enum class ShiftAction {
        OPEN, CLOSE, SMART
    }

    class Output(val shiftState: ShiftState)

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): ShiftState {

        val kktAction = input?.forceState ?: ShiftAction.SMART

        val kktPort = if (input?.cashier == null) {
            GetKktPort(commonPort).invoke().kktPort
        } else {
            GetKktPort(commonPort).invoke(GetKktPort.Input(cashier = input.cashier)).kktPort
        }

        val shiftState = kktPort.getShiftState()


        when (kktAction) {
                ShiftAction.CLOSE -> kktPort.closeShift()
                ShiftAction.OPEN -> kktPort.openShift()
                ShiftAction.SMART -> {
                    when (shiftState) {
                        ShiftState.OPENED, ShiftState.EXPIRED -> kktPort.closeShift()
                        ShiftState.CLOSED -> kktPort.openShift()
                        ShiftState.UNDEFINED -> throw IllegalArgumentException("Неизвестное состояние смены: $shiftState")
                    }
                }
        }

        if (input?.deletePortAfterUse == true) {
            commonPort.kktPortFactory.deleteInstance(KktType.ATOL)
        }


        val currentShiftState = kktPort.getShiftState()

        if (input?.isPoolerControl == true) {
            when (currentShiftState) {
                ShiftState.OPENED -> PoolingServiceControl(commonPort).invoke(PoolingServiceControl.PoolingCommand.START, sceneManager)
                else -> PoolingServiceControl(commonPort).invoke(PoolingServiceControl.PoolingCommand.STOP, sceneManager)
            }
        }


        sceneManager?.let {
            present(currentShiftState, it)
        }

        return currentShiftState
    }

    override fun present(output: ShiftState, sceneManager: SceneManager) {
        sceneManager.showNotification(
            NotificationType.INFO,
            "Операция с кассой",
            "Операция по изменению состояния смены успешно завершена."
        )
        sceneManager.getPresenter(MainViewController::class)?.setShiftState(output)
    }

}