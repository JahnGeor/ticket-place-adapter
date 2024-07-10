package ru.kidesoft.ticketplace.adapter.ui.presenter

import net.datafaker.Faker
import ru.kidesoft.ticketplace.adapter.domain.Cashier

interface AuthViewGetter {}

interface AuthViewSetter {
    fun setUrls(urls: List<String>)
    fun setEmails(emails: List<String>)
    fun setCashiers(cashiers: List<Cashier>)
}

interface AuthView : AuthViewGetter, AuthViewSetter, ViewManager {

}

class AuthPresenter() {
    fun login(email: String, password: String, url: String, authView: AuthView) {

    }

    fun logout() {}

    fun update(authView: AuthView) {
        // throw IllegalArgumentException("There is a mistake")


        // TODO: Получаем данные, необходимые для заполнения
//        view.setUrls()
//        view.setCashiers()
//        view.setEmails()
    }
}
