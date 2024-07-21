package ru.kidesoft.ticketplace.adapter.infrastructure.api.web

import ru.kidesoft.ticketplace.adapter.application.port.ApiPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.ApiPort
import ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace.TicketPlaceApiImpl
import java.time.Duration

const val TicketPlaceMain = "https://ticket-place.ru"
const val TicketPlaceDev = "https://tiplace.ru"

class WebServerApiPortFactory() : ApiPortFactory {
    override fun getInstance(url: String, token: String?, tokenType: String?, timeout: Duration?, userId: Int?): ApiPort {
        return when (url) {
            TicketPlaceMain, TicketPlaceDev -> TicketPlaceApiImpl(url).apply {
                this.token = token
                this.timeout = timeout?.let { it }?: Duration.ofSeconds(10)
                this.tokenType = tokenType
                this.userId = userId
            }
            else -> {
                throw IllegalArgumentException("В списке доступных веб-серверов нет выбранного вами сервера")
            }
        }
    }


    override fun getApis(): List<String> {
        return listOf(TicketPlaceMain)
    }


}