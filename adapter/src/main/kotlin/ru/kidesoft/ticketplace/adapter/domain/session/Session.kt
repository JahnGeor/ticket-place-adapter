package ru.kidesoft.ticketplace.adapter.domain.session

import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.UUID
import kotlin.properties.Delegates

class Session {
    lateinit var id : UUID
    lateinit var loginId: UUID
    var active = false
    var token : Token = Token()

    fun isExpired() : Boolean {
        return this.token.expiredTime.isBefore(ZonedDateTime.now())
    }
}

class Token {
    lateinit var type : String
    lateinit var value : String
    lateinit var expiredTime : ZonedDateTime
    lateinit var createdTime : ZonedDateTime
}

class SessionExposed {
    lateinit var loginId: UUID
    lateinit var token : Token
    var active = false
}