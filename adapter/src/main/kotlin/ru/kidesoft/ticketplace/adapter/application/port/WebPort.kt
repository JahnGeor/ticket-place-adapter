package ru.kidesoft.ticketplace.adapter.application.port

import ru.kidesoft.ticketplace.adapter.application.dto.LoginData

interface ApiPort {
    suspend fun login(email: String, password: String): LoginData
    fun getOrder()
    fun getClick()
}

interface ApiFactory {
    fun getInstance(url : String): ApiPort

    fun getApis() : List<String>
}