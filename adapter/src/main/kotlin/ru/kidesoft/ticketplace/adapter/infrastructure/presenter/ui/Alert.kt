package ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui

interface Alert {
    fun onErrorAlert(e : Exception)
    fun onWarningAlert()
    fun onInformationAlert()
}