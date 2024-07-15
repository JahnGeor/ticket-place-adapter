package ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.kidesoft.ticketplace.adapter.application.dto.LoginData
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.profile.ProfileExposed
import ru.kidesoft.ticketplace.adapter.domain.profile.RoleType
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.domain.session.SessionExposed
import ru.kidesoft.ticketplace.adapter.domain.session.Token
import java.lang.reflect.Type
import java.time.ZonedDateTime
import java.util.*
import kotlin.properties.Delegates

class LoginData : LoginData {
    lateinit var accessToken: String
    lateinit var tokenType: String
    var userId by Delegates.notNull<Long>()
    lateinit var fullName: String;
    lateinit var username: String;
    lateinit var avatar: String;
    lateinit var role: String;
    var inn by Delegates.notNull<Long>();


    override fun mapToProfile(): ProfileExposed {
        return ProfileExposed().apply {
            this.avatar = this@LoginData.avatar
            this.roleType = RoleType.fromName(this@LoginData.role)
            this.userId = this@LoginData.userId
            this.userName = this@LoginData.username

            this.cashier = Cashier().apply {
                this.inn = this@LoginData.inn
                this.fullName = this@LoginData.fullName
            }
        }
    }

    override fun mapToSession(): SessionExposed {
        return SessionExposed().apply {
            this.token = Token().apply {
                this.type = this@LoginData.tokenType
                this.value = this@LoginData.accessToken
                this.createdTime = ZonedDateTime.now() // TODO: добавить чтение даты из строки по формату
                this.expiredTime = ZonedDateTime.now().plusDays(1) // TODO: добавить чтение даты из строки по формату
            }
        }
    }
}

class LoginDataTypeAdapter :
    JsonDeserializer<ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace.LoginData> {
    override fun deserialize(
        jsonElement: JsonElement,
        p1: Type?,
        p2: JsonDeserializationContext?
    ): ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace.LoginData {
        val jsonObject = jsonElement.asJsonObject
        val accessToken = jsonObject.get("accessToken").asString
        val tokenType = jsonObject.get("token_type").asString
        val userData = jsonObject.get("userData").asJsonObject

        val userId = userData.get("id").asLong
        val fullName = userData.get("fullName").asString
        val username = userData.get("username").asString
        val avatar = userData.get("avatar").asString
        val role = userData.get("role").asString
        val inn = userData.get("inn").asLong

        return LoginData().apply {
            this.accessToken = accessToken
            this.tokenType = tokenType
            this.userId = userId
            this.fullName = fullName
            this.username = username
            this.avatar = avatar
            this.role = role
            this.inn = inn
        }
    }

}

