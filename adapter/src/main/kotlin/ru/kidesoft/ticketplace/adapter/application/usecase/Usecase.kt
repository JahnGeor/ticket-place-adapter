package ru.kidesoft.ticketplace.adapter.application.usecase

abstract class Usecase<I : Usecase.InputValues, O : Usecase.OutputBoundary> {
    abstract fun execute(inputValues: I, outputBoundary: O)

    interface InputValues {}

    interface OutputBoundary {}
}