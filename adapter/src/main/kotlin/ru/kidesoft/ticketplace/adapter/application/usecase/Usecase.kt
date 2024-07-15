package ru.kidesoft.ticketplace.adapter.application.usecase

import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter

abstract class _Usecase<I : _Usecase.Input, O : _Usecase.Output, P : Presenter> {
    interface Input {}
    interface Output {}

    abstract suspend fun execute(input: I): O

    abstract fun present(output: O, presenter: P)
}