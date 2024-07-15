package ru.kidesoft.ticketplace.adapter.domain.login

import java.util.UUID

class LoginExposed() {
    lateinit var email: String
    lateinit var url: String
    lateinit var password: String
}

class Login {
    lateinit var id: UUID
    lateinit var email: String
    lateinit var url: String
    lateinit var password: String
}