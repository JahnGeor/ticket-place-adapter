package ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import ru.kidesoft.ticketplace.adapter.application.dto.LoginData
import ru.kidesoft.ticketplace.adapter.application.dto.Mapper
import ru.kidesoft.ticketplace.adapter.application.port.ApiPort
import ru.kidesoft.ticketplace.adapter.domain.click.ClickInfo
import ru.kidesoft.ticketplace.adapter.domain.order.Order
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.time.Duration

class TicketPlaceApiImpl(val url: String) : ApiPort {
    var token: String? = null
    var timeout: Duration? = Duration.ofSeconds(10)
    var tokenType: String? = null
    var userId : Int? = null

    private fun validate() {
        token ?: throw IllegalArgumentException("token is null")
        tokenType ?: throw IllegalArgumentException("tokenType is null")
    }

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

    override suspend fun getOrder(orderId: Int, sourceType: SourceType): Mapper<Order> {
        validate()

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

    override suspend fun getClick(): Mapper<ClickInfo> {
        validate()

        val client = HttpClient(CIO) {
            engine {
                requestTimeout = timeout?. toMillis() ?: throw RuntimeException("timeout is missing")
            }

            expectSuccess = true

            install(ContentNegotiation) {
                gson {
                    registerTypeAdapter(ClickData::class.java, ClickDataAdapter())
                }
            }
        }

        return client.get(url) {
            url {
                appendPathSegments("api", "print-requests", "by-user", userId.toString())
            }
            method = HttpMethod.Get

            headers {
                header("Content-Type", "application/json")
                header("Authorization", "$tokenType $token")
            }
        }.body<ClickData>()
    }

}