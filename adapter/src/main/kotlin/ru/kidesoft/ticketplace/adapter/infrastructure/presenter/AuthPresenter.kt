package ru.kidesoft.ticketplace.adapter.infrastructure.presenter

import ru.kidesoft.ticketplace.adapter.domain.Cashier
import ru.kidesoft.ticketplace.adapter.domain.Login
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import java.lang.Exception
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext

interface AuthViewGetter {}

interface AuthViewSetter {
    fun setUrls(urls: List<String>)
    fun setEmails(emails: List<String>)
    fun setCashiers(cashiers: List<Cashier>)
}

interface AuthView : AuthViewGetter, AuthViewSetter, ViewManager {

}

class AuthPresenter() : Presenter() {
    fun login(email: String, password: String, url: String, authView: AuthView) {

    }

    fun logout(viewManager : ViewManager) {
        try {
            TODO("authUsecase.Logout() is not implemented")
            viewManager.getSceneManager().openScene(Scene.AUTH)
        } catch (error : Throwable) {
            // TODO: handle(error, viewManager) (возможно, стоит использовать несколько handlers -> один с отображением ошибки и передачей viewManager, второй - без)
            viewManager.getSceneManager().onErrorAlert(Exception(error))
        }

    }

    fun update(authView: AuthView) {
        // throw IllegalArgumentException("There is a mistake")


        // TODO: Получаем данные, необходимые для заполнения
//        view.setUrls()
//        view.setCashiers()
//        view.setEmails()
    }
}