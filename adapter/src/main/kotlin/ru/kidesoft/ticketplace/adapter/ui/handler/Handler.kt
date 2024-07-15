package ru.kidesoft.ticketplace.adapter.ui.handler

import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter

interface Handler {
    fun handle(exception: Throwable, presenter: Presenter)
}