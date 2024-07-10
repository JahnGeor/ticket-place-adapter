package ru.kidesoft.ticketplace.adapter.infrastructure.presenter

interface BaseView : ViewManager {

}

class BasePresenter : Presenter() {
    fun runDiagnostic() {}

    fun exit(b: BaseView) {
        try {
            b.getSceneManager().closeApplication()
        } catch (e: Exception) {
            b.getSceneManager().onErrorAlert(e)
            println(e.cause)
        }
    }

    fun runDiagnostic(b: BaseView) {

    }
}