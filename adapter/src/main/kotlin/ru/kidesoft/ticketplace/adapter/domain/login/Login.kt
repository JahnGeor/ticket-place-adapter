package ru.kidesoft.ticketplace.adapter.domain.login

import java.util.UUID

data class LoginInfo(val email: String, val url: String, val password: String)

data class Login (val id: UUID, val email: String, val url: String, val password: String) {
    constructor(id: UUID, loginInfo: LoginInfo) : this(id, loginInfo.email, loginInfo.url, loginInfo.password)

    fun toLoginInfo(): LoginInfo {
        return LoginInfo(email, url, password)
    }
}