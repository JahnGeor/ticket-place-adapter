package ru.kidesoft.ticketplace.adapter.ui.handler

import ru.kidesoft.ticketplace.adapter.application.presenter.AlertType
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager

class DefaultHandler(val sceneManager: SceneManager) : Handler {
    override fun handle(exception: Throwable) {
        // TODO: сделать разветвление, пока просто - ошибки
        sceneManager.showAlert(AlertType.ERROR, exception.message?.let { it }?:exception.cause?.message?.let { it }?:"Неизвестная ошибка", "Во время выполнения операции произошла ошибка")
    }
}