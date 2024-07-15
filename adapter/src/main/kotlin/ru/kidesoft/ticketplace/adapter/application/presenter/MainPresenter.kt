package ru.kidesoft.ticketplace.adapter.application.presenter

import net.datafaker.providers.base.Bool
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile

interface MainPresenter : Presenter {
    fun setProfile(profile : Profile)

    fun setKktState(isConnected: Boolean, shiftState: ShiftState)
}