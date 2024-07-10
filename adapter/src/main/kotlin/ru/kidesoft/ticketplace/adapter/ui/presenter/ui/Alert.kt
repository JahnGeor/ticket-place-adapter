package ru.kidesoft.ticketplace.adapter.ui.presenter.ui

interface Alert {
    fun onErrorAlert(e : Exception)
    fun onWarningAlert()
    fun onInformationAlert()
}