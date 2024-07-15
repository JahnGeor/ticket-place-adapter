package ru.kidesoft.ticketplace.adapter.application.usecase.session

import org.jetbrains.exposed.sql.not
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase

class IsActiveSessionUsecase(private val databasePort: DatabasePort) : _Usecase<IsActiveSessionUsecase.Input, IsActiveSessionUsecase.Output, Presenter>() {

    class Input : _Usecase.Input {}

    class Output(val isActive : Boolean) : _Usecase.Output {}

    override suspend fun execute(inputValues: Input): Output {
        return Output(isActive = databasePort.getSession().getActive() != null )
    }

    override fun present(output: Output, presenter: Presenter) {
        TODO("Not yet implemented")
    }
}