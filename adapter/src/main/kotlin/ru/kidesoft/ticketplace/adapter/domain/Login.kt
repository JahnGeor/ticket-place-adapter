package ru.kidesoft.ticketplace.adapter.domain

import java.util.UUID

data class Login(var uuid : UUID, var email : String, var password : String, var url : String) {

}
