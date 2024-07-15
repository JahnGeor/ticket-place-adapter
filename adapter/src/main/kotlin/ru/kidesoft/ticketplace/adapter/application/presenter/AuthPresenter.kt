package ru.kidesoft.ticketplace.adapter.application.presenter

import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier

interface AuthPresenter : Presenter {
    fun setEmails(emailList: List<String>)
    fun setUrls(urlList: List<String>)
    fun setCashiers(cashiers: List<Cashier>)
}