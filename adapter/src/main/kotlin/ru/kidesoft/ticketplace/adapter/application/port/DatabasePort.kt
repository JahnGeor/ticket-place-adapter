package ru.kidesoft.ticketplace.adapter.application.port

interface DatabasePort {
    fun getLogin() : LoginPort
    fun getSession() : SessionPort
    fun getClick() : ClickPort
    fun getProfile() : ProfilePort
    fun getSetting() : SettingPort
    fun getHistory() : HistoryPort
}

interface LoginPort {

}

interface SessionPort {

}

interface ClickPort {

}

interface ProfilePort {

}

interface SettingPort {

}

interface HistoryPort {

}