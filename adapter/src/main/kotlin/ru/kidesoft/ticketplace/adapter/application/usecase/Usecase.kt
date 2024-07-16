package ru.kidesoft.ticketplace.adapter.application.usecase

import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager

abstract class _Usecase<I : _Usecase.Input, O : _Usecase.Output> {
    interface Input {}
    interface Output {}

    abstract suspend fun execute(input: I? = null): O

    abstract fun present(output: O, sceneManager: SceneManager)
}