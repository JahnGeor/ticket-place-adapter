package ru.kidesoft.ticketplace.adapter.application.presenter.dialog

import kotlinx.coroutines.Deferred
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter

interface ClickDialog : Presenter {
    suspend fun showAndWait(orderId: Int, clickId: Int) : Boolean
    fun close()
}