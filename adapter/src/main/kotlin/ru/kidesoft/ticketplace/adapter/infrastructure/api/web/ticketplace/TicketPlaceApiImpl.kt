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
import ru.kidesoft.ticketplace.adapter.application.dto.LoginData
import ru.kidesoft.ticketplace.adapter.application.port.ApiPort
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import kotlin.properties.Delegates
import kotlin.time.Duration

class TicketPlaceApiImpl(val url: String) : ApiPort {
    lateinit var token: String
    var timeout: Duration = Duration.ZERO
    lateinit var tokenType: String

    override suspend fun login(email: String, password: String): LoginData {
        // test Data:
        val client = HttpClient(CIO) {
            engine {
                requestTimeout = 10_000
            }

            expectSuccess = true

            install(ContentNegotiation) {
                // GsonBuilder().registerTypeAdapter(ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace.LoginData::class.java, LoginDataTypeAdapter()).create()
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


    //.body<ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace.LoginData>()




//        return LoginData().apply {
//            accessToken = "Some token"
//            tokenType = "Bearer"
//            userId = 1
//            fullName = "Тестовый пользователь"
//            username = "test"
//            avatar = "some avatar"
//            role = "admin"
//            inn = 12341412412
//        }
    }

    override fun getOrder() {
        TODO("Not yet implemented")
    }

    override fun getClick() {
        TODO("Not yet implemented")
    }
}