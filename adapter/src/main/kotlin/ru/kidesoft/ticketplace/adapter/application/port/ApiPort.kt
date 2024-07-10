package ru.kidesoft.ticketplace.adapter.application.port

interface ApiPort {

}

interface ApiFactory {
    fun getInstance() : ApiPort
}