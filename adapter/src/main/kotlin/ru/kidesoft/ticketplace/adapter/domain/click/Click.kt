package ru.kidesoft.ticketplace.adapter.domain.click

import java.util.UUID
import kotlin.properties.Delegates

class Click {
    lateinit var id : UUID
    lateinit var loginId : UUID
    var clickId by Delegates.notNull<Int>()
}