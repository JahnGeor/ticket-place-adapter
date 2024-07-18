package ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace

import com.google.gson.GsonBuilder
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.serialization.serializer
import org.apache.logging.log4j.core.config.Order
import ru.kidesoft.ticketplace.adapter.application.dto.LoginData
import ru.kidesoft.ticketplace.adapter.application.dto.Mapper
import ru.kidesoft.ticketplace.adapter.application.port.ApiPort
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import ru.kidesoft.ticketplace.adapter.domain.order.OrderExposed
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.time.Duration
import java.util.concurrent.TimeoutException
import kotlin.properties.Delegates

class TicketPlaceApiImpl(val url: String) : ApiPort {
    var token: String? = null
    var timeout: Duration? = Duration.ofSeconds(10)
    var tokenType: String? = null

    override suspend fun login(email: String, password: String): LoginData {
        val client = HttpClient(CIO) {
            engine {
                requestTimeout = timeout?.toMillis()?: throw RuntimeException("Timeout can't be null")
            }

            expectSuccess = true

            install(ContentNegotiation) {
                gson {
                    registerTypeAdapter(ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace.LoginData::class.java, LoginDataTypeAdapter())
                }
            }
        }

         return client.post(url) {
            url {
                appendPathSegments("api", "auth", "login")
                parameters.append("email", email)
                parameters.append("password", password)
            }


            method = HttpMethod.Post
        }.body<ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace.LoginData>()
    }

    override suspend fun getOrder(orderId: Int, sourceType: SourceType): Mapper<OrderExposed> {

        token ?: throw IllegalArgumentException("token is null")
        tokenType ?: throw IllegalArgumentException("tokenType is null")



        val client = HttpClient(CIO) {
            engine {
                requestTimeout = timeout?. toMillis() ?: throw RuntimeException("timeout is missing")
            }

            expectSuccess = true

            install(ContentNegotiation) {
                gson {
                    registerTypeAdapter(OrderData::class.java, OrderAdapter())
                }
            }
        }

        val endpointSourceType = when (sourceType) {
            SourceType.ORDER -> "order"
            SourceType.REFUND -> "refund"
            else -> throw IllegalArgumentException("Illegal source type: $sourceType")
        }

        return client.post(url) {
            url {
                appendPathSegments("api", endpointSourceType, orderId.toString())
            }
            method = HttpMethod.Post

            headers {
                header("Content-Type", "application/json")
                header("Authorization", "$tokenType $token")
            }
        }.body<OrderData>()
    }

    override fun getClick(userId: Int) {
        TODO("Not yet implemented")
    }

}