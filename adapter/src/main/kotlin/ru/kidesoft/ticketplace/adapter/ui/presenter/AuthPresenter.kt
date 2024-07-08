package ru.kidesoft.ticketplace.adapter.ui.presenter

import ru.kidesoft.ticketplace.adapter.domain.Cashier


interface View {
    fun setUrls(urls : List<String>)
    fun setEmails(emails : List<String>)
    fun setCashiers(cashiers : List<Cashier>)
}



class AuthPresenter() {
    fun login(email: String, password: String, url: String, view : View) {

    }

    fun logout() {}

    fun update(view : View) {
        // TODO: Получаем данные, необходимые для заполнения
//        view.setUrls()
//        view.setCashiers()
//        view.setEmails()
    }
}
