package ru.kidesoft.ticketplace.adapter.application.usecase.kkt

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.exception.ValidationException

class CashIncomeAction(commonPort: CommonPort) : Usecase<CashIncomeAction.Input, CashIncomeAction.Output>(commonPort) {
    class Input {
        var float: Float = 0.0f
    }
    class Output

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val output = Output()

        // Валидация данных через ValidationError

        val income = validate(input)

        val kktPort = GetKktPort(commonPort).invoke().kktPort

        kktPort.cashIncome(income)

        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {

    }

    private fun validate(input: Input?) : Float {
        if (input == null) {
            throw RuntimeException("В вариант использования $this не переданы данные")
        }

        if (input.float <= 0.0f) {
            throw ValidationException("Сумма не можем быть меньше нуля")
        }

        return input.float
    }
}