package ru.kidesoft.ticketplace.adapter.application.usecase

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.domain.history.HistoryPayload
import ru.kidesoft.ticketplace.adapter.domain.history.Step

class SaveHistory(commonPort: CommonPort) : Usecase<SaveHistory.Input, SaveHistory.Output>(commonPort) {


    class Input : Usecase.Input {
        var orderId: Int? = null
        var historyPayload: HistoryPayload? = null
        var step: Step = Step.UNDEFINED
    }
    class Output : Usecase.Output

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val output = Output()

        val history = commonPort.databasePort.getHistory().save(input!!.orderId!!, input.step, input.historyPayload!!)

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {

    }

    private fun validate(input: Input?) {
        input?.let {
            if (it.orderId == null || it.historyPayload == null) {
                throw NullPointerException("Input order or history payload of ${this::class.simpleName} cannot be null.")
            }
        }?: throw IllegalArgumentException("Input of ${this::class.simpleName} cannot be null")
    }
}