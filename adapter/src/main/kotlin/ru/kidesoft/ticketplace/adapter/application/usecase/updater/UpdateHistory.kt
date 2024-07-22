package ru.kidesoft.ticketplace.adapter.application.usecase.updater

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.HistoryPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.history.History
import java.time.ZonedDateTime

class UpdateHistory(commonPort: CommonPort) : Usecase<UpdateHistory.Input, UpdateHistory.Output>(commonPort) {
    class Input(var from: ZonedDateTime? = null)


    class Output(val historyList: List<History>)

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val from = input?.from

        val list = commonPort.databasePort.getHistory().getListByCurrent(from)

        val output = Output(list)

        sceneManager?.let {
            present(output, sceneManager)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.getPresenter(HistoryPresenter::class)?.let {
            it.setHistory(output.historyList)
        } ?: throw NullPointerException("${HistoryPresenter::class::simpleName} is not found")
    }
}