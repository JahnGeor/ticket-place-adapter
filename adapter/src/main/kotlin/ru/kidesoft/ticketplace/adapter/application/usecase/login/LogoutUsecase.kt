package ru.kidesoft.ticketplace.adapter.application.usecase.login

import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase

class LogoutUsecase : Usecase<LogoutUsecase.Input, LogoutUsecase.OutputBoundary>() {
    class Input : Usecase.InputValues {

    }

    interface OutputBoundary : Usecase.OutputBoundary {

    }

    override fun execute(inputValues: Input, outputBoundary: OutputBoundary) {
        TODO("Not yet implemented")
    }
}