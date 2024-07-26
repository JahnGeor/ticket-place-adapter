package ru.kidesoft.ticketplace.adapter.domain.session

import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.UUID
import kotlin.properties.Delegates

data class Session(val id : UUID, val loginId : UUID, val token: Token, val active: Boolean) {

    constructor(id: UUID, loginId : UUID, sessionInfo: SessionInfo, active: Boolean = false) : this(id, loginId, sessionInfo.token, active) {}

    fun isExpired() : Boolean {
        return this.token.expiredTime.isBefore(ZonedDateTime.now())
    }
}

data class Token(val type : String, val value: String, val createdTime : ZonedDateTime, val expiredTime : ZonedDateTime)

data class SessionInfo(var token: Token)

