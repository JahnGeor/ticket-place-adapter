package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import ru.kidesoft.ticketplace.adapter.application.port.ApiPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase

class CashIncomeAction(commonPort: CommonPort) : Usecase<CashIncomeAction.Input, CashIncomeAction.Output>(commonPort) {
    class Input : Usecase.Input {
        var float: Float = 0.0f
    }
    class Output : Usecase.Output

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val output = Output()

        if (input == null) {
            throw IllegalArgumentException("Input cannot be null!")
        }

        // Валидация данных через ValidationError

        val income = input.float

        var kktPort = GetCurrentKktPort(commonPort).invoke().kktPort

        kktPort.cashIncome(income)

        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {

    }
}