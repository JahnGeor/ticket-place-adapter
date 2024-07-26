package ru.kidesoft.ticketplace.adapter.application.dto

import ru.kidesoft.ticketplace.adapter.domain.profile.ProfileInfo
import ru.kidesoft.ticketplace.adapter.domain.session.SessionInfo

interface LoginData {
    fun mapToProfile() : ProfileInfo
    fun mapToSession() :  SessionInfo
}

interface Mapper<I> {
    fun mapToEntity() : I
}