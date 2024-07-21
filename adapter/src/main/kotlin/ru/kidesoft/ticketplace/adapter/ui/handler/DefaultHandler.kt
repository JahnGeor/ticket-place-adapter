package ru.kidesoft.ticketplace.adapter.ui.handler

import ru.kidesoft.ticketplace.adapter.application.presenter.AlertType
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.GetCurrentKktPort
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.StartKktSession
import ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt.atol.KktException
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor

class DefaultHandler(val sceneManager: SceneManager) : Handler {


    override fun handle(exception: Throwable) {
        // TODO: сделать разветвление, пока просто - ошибки

    when (exception) {
        is KktException -> kktHandle(exception)
        else -> defaultHandler(exception)
    }

    }

    fun kktHandle(e: KktException) {
        sceneManager.showAlert(AlertType.ERROR, "Во время работы с кассой произошла ошибка", e.message?.let { it }?:e.cause?.message?.let { it }?:"Неизвестная ошибка")

        when(e.code) {
           // 0 ->
        }
    }

    fun defaultHandler(e: Throwable) {
        sceneManager.showAlert(AlertType.ERROR, "Во время выполнения операции произошла ошибка", "Ошибка неизвестного типа", e)
    }
}