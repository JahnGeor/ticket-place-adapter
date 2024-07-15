package ru.kidesoft.ticketplace.adapter.application.usecase.profile

import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.AuthPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.login.GetAllLoginUsecase
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier

class GetCashierListUsecase(private val databasePort: DatabasePort) :
    _Usecase<GetCashierListUsecase.GetCashierListInput, GetCashierListUsecase.GetCashierListOutput, AuthPresenter>() {

    data class GetCashierListOutput(val cashierList : List<Cashier>) : _Usecase.Output {}

    class GetCashierListInput : _Usecase.Input {}

    override suspend fun execute(inputValues: GetCashierListInput): GetCashierListOutput {
        val cashiers = databasePort.getProfile().getCashierList()
        return GetCashierListOutput(cashiers)
    }

    override fun present(output: GetCashierListOutput, presenter: AuthPresenter) {
        presenter.setCashiers(cashiers = output.cashierList)
    }
}