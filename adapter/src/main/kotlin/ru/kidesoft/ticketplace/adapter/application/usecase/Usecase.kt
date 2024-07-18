package ru.kidesoft.ticketplace.adapter.application.usecase

import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.updater.UpdateAbout.Output

abstract class _Usecase<I : _Usecase.Input, O : _Usecase.Output> {
    interface Input {}
    interface Output {}

    abstract suspend fun invoke(input: I? = null, sceneManager: SceneManager? = null): O

    open fun present(output: O, sceneManager: SceneManager) : Unit  = Unit

}