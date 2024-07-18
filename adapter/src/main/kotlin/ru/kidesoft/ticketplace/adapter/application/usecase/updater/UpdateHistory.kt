package ru.kidesoft.ticketplace.adapter.application.usecase.updater

import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.HistoryPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.domain.history.History

class UpdateHistory(private val databasePort: DatabasePort) : _Usecase<UpdateHistory.Input, UpdateHistory.Output>() {
    class Input : _Usecase.Input {}
    class Output(val historyList: List<History>) : _Usecase.Output {}

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val list = databasePort.getHistory().getListByCurrentUser()

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