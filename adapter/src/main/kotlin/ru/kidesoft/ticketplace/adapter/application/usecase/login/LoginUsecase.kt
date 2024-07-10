package ru.kidesoft.ticketplace.adapter.application.usecase.login

import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase

class LoginUsecase : Usecase<LoginUsecase.Input, LoginUsecase.OutputBoundary>() {
    open class Input : Usecase.InputValues {

    }

    interface OutputBoundary : Usecase.OutputBoundary {
        fun changeScene()
    }

    override fun execute(inputValues: Input, outputBoundary: OutputBoundary) {
        TODO("Not yet implemented")
    }


}