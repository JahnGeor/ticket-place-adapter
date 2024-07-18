package ru.kidesoft.ticketplace.adapter.application.dto

import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.profile.ProfileExposed
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.domain.session.SessionExposed

interface LoginData {
    fun mapToProfile() : ProfileExposed
    fun mapToSession() : SessionExposed
}

interface Mapper<I> {
    fun mapToEntity() : I
}