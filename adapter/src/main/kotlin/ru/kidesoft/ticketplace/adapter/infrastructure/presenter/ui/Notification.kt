package ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui

interface Notification {
    fun onError(e : Exception)
    fun onWarning()
}