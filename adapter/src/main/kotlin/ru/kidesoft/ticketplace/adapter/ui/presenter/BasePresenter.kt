package ru.kidesoft.ticketplace.adapter.ui.presenter

import ru.kidesoft.ticketplace.adapter.ui.presenter.ui.Scene

interface BaseView : ViewManager{

}

class BasePresenter : Presenter() {
    fun runDiagnostic() {}

    fun exit(b: BaseView) {
        try {
            b.getSceneManager().openScene(Scene.AUTH)
        } catch (e: Exception) {
            b.getSceneManager().onErrorAlert(e)
            println(e.cause)
        }
    }
}