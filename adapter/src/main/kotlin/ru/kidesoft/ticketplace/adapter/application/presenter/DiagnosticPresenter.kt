package ru.kidesoft.ticketplace.adapter.application.presenter

import net.datafaker.providers.base.Bool

interface DiagnosticPresenter : Presenter {
    fun setStates()
    fun setRecommendation(recommendation: String)
    fun setReason(reason: String)
    fun setRecoveryState(recoveryState: Boolean)
}