package ru.kidesoft.ticketplace.adapter.application.usecase

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import ru.kidesoft.ticketplace.adapter.application.port.ApiPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager

abstract class Usecase<I : Usecase.Input, O : Usecase.Output>(val commonPort: CommonPort) {
    val logger: Logger = LogManager.getLogger(this::class.java)

    interface Input {}
    interface Output {}

    abstract suspend fun invoke(input: I? = null, sceneManager: SceneManager? = null): O

    open fun present(output: O, sceneManager: SceneManager) : Unit  = Unit

}