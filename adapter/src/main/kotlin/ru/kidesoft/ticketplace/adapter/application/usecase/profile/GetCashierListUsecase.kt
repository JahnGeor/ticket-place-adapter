package ru.kidesoft.ticketplace.adapter.application.usecase.profile

import ru.kidesoft.ticketplace.adapter.application.port.ApiPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.AuthPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier

class GetCashierListUsecase(commonPort: CommonPort) :
    Usecase<GetCashierListUsecase.GetCashierListInput, GetCashierListUsecase.GetCashierListOutput>(commonPort) {

    data class GetCashierListOutput(val cashierList : List<Cashier>)

    class GetCashierListInput

    override suspend fun invoke(inputValues: GetCashierListInput?, sceneManager: SceneManager?): GetCashierListOutput {
        val cashiers = commonPort.databasePort.getProfile().getCashierList()

        sceneManager?.let {
            it.getPresenter(AuthPresenter::class)?.setCashiers(cashiers = cashiers)
        }

        return GetCashierListOutput(cashiers)
    }
}