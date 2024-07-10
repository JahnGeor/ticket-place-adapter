package ru.kidesoft.ticketplace.adapter.ui.presenter.ui

interface Notification {
    fun onError(e : Exception)
    fun onWarning()
}