package ru.kidesoft.ticketplace.adapter.infrastructure.api.web

import ru.kidesoft.ticketplace.adapter.application.port.ApiFactory
import ru.kidesoft.ticketplace.adapter.application.port.ApiPort
import ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace.TicketPlaceApiImpl
import kotlin.time.Duration

const val TicketPlaceMain = "https://ticket-place.ru"

class WebServerApiFactory() : ApiFactory {
    override fun getInstance(url: String): ApiPort {
        return when (url) {
            TicketPlaceMain -> TicketPlaceApiImpl(TicketPlaceMain)
            else -> {
                throw IllegalArgumentException("В списке доступных веб-серверов нет выбранного вами сервера")
            }
        }
    }

    override fun getApis(): List<String> {
        return listOf(TicketPlaceMain)
    }


}