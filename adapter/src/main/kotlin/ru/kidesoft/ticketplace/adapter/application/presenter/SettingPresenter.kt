package ru.kidesoft.ticketplace.adapter.application.presenter

import ru.kidesoft.ticketplace.adapter.domain.setting.Setting


interface SettingPresenter : Presenter {
    fun setSetting(setting: Setting)
}