package ru.kidesoft.ticketplace.adapter.application.presenter

import ru.kidesoft.ticketplace.adapter.domain.history.History

interface HistoryPresenter : Presenter {
    fun setHistory(history : List<History>)
    fun addHistory(history : History)
}