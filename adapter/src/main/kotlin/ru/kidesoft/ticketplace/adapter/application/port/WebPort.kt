package ru.kidesoft.ticketplace.adapter.application.port

import ru.kidesoft.ticketplace.adapter.application.dto.LoginData
import ru.kidesoft.ticketplace.adapter.application.dto.Mapper
import ru.kidesoft.ticketplace.adapter.domain.order.OrderExposed
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.time.Duration

interface ApiPort {
    suspend fun login(email: String, password: String): LoginData
    suspend fun getOrder(orderId : Int, sourceType: SourceType) : Mapper<OrderExposed>
    fun getClick(userId: Int)
}

interface ApiFactory {
    fun getInstance(url : String, token: String? = null, tokenType: String? = null, timeout: Duration? = null): ApiPort
    fun getApis() : List<String>
}