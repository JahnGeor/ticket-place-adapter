package ru.kidesoft.ticketplace.adapter.ui.handler

import ru.kidesoft.ticketplace.adapter.application.presenter.AlertType
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter

class DefaultHandler : Handler {
    override fun handle(exception: Throwable, presenter: Presenter) {
        // TODO: сделать разветвление, пока просто - ошибки
        presenter.getSceneManager().showAlert(AlertType.ERROR, exception.message?.let { it }?:exception.cause?.message?.let { it }?:"Неизвестная ошибка", "Во время выполнения операции произошла ошибка")
    }

}