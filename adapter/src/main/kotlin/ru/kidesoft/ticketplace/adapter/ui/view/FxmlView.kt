package ru.kidesoft.ticketplace.adapter.ui.view

import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import kotlinx.coroutines.*

annotation class FxmlView(
    val path : String,
    val scene : Scene
)