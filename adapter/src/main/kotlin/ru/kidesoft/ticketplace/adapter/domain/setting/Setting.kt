package ru.kidesoft.ticketplace.adapter.domain.setting

import kotlin.properties.Delegates

class Setting {
    val kkt = KktSetting()
}


class KktSetting {
    lateinit var path: String
    var autoRecconect by Delegates.notNull<Boolean>()
}